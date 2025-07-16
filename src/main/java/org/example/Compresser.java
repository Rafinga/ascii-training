package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Compresser {



    private final String path;
    public Compresser(final String path) throws IOException {
        this.path = path;
    }

    public void compress(final double xScale, final double yScale) throws IOException {
        BufferedImage grayImage =  ImageIO.read(new File(this.path));
        final int width = grayImage.getWidth();
        final int height  = grayImage.getHeight();

        int newWidth = (int) (width* xScale);
        int newHeight = (int) (height* yScale);

        // Create new image
        BufferedImage resized = new BufferedImage(newWidth, newHeight, grayImage.getType());

        // Draw scaled image
        Graphics2D g = resized.createGraphics();
        g.drawImage(grayImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        // Save result (optional)
        File output = new File("resized_output.jpg");
        ImageIO.write(resized, "jpg", output);
        System.out.println("Saved compressed image to: " + output.getAbsolutePath());

    }

}
