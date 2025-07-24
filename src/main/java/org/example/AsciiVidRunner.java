package org.example;



import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.opencv.global.opencv_core;

import static org.bytedeco.opencv.global.opencv_core.getBuildInformation;

import org.bytedeco.javacv.*;

import java.awt.image.BufferedImage;

public class AsciiVidRunner {


  public static void run(final String vidName) throws Exception {
    Ascifier ascifier = new Ascifier();
//    System.out.println("OpenCV version: " + opencv_core.CV_VERSION);
//    System.out.println("Loaded OpenCV libraries:");
//    BytePointer infoPtr = getBuildInformation();
//    String buildInfo = infoPtr.getString();
//    System.out.println(buildInfo);
//    String vidName2 = "/home/yoshicabeza/ascii-training/build/resources/main/enderman_dance.mp4";
    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(vidName);
    Java2DFrameConverter converter = new Java2DFrameConverter();

    try {
      grabber.start();

      Frame frame;
      int frameIndex = 0;

      while ((frame = grabber.grabImage()) != null) {
        BufferedImage image = converter.convert(frame);
        ascifier.ascify(image);
        System.out.println("Frame " + frameIndex++);

        // Optional: Slow down output to simulate video
        Thread.sleep(1000 / 120); // ~60 FPS
      }

      grabber.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
