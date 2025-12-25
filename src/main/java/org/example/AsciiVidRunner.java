package org.example;



import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.opencv.global.opencv_core;

import static org.bytedeco.opencv.global.opencv_core.getBuildInformation;

import org.bytedeco.javacv.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AsciiVidRunner {


  public static void run(final String vidName, final int speed, final TextPix.CharacterSet charset, final int blockWidth, final int blockHeight) throws Exception {
    Ascifier ascifier = new Ascifier(charset, blockWidth, blockHeight);
    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(vidName);
    Java2DFrameConverter converter = new Java2DFrameConverter();

    // Standard dimensions (720p)
    final int targetWidth = 1280;
    final int targetHeight = 720;

    try {
      grabber.start();

      org.bytedeco.javacv.Frame frame;
      int frameIndex = 0;
      final int baseSleep = 1000 / 120; // 8ms base sleep
      final int skipFrames = speed > baseSleep ? speed / baseSleep : 1;

      while ((frame = grabber.grabImage()) != null) {
        if (frameIndex % skipFrames == 0) {
          // Move cursor to top-left corner (overwrite previous frame)
          System.out.print("\033[H");
          
          BufferedImage image = converter.convert(frame);
          
          // Rescale image to standard size
          BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
          Graphics2D g2d = scaledImage.createGraphics();
          g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
          g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);
          g2d.dispose();
          
          ascifier.ascify(scaledImage);
          System.out.println("Frame " + frameIndex);

          // Sleep only if speed doesn't require frame skipping
          if (speed <= baseSleep) {
            Thread.sleep(baseSleep / speed);
          }
        }
        frameIndex++;
      }

      grabber.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
