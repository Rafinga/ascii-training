package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Displayer {
    private final InputStream fileStream;
    public Displayer(final InputStream fileStream){
        this.fileStream = fileStream;
    }
    public void display() throws IOException {
        BufferedImage img = ImageIO.read(this.fileStream);
        img.createGraphics();
        // Create a JFrame to display it
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(img.getWidth(), img.getHeight()));

        // Add image to a JLabel
        JLabel label = new JLabel(new ImageIcon(img));
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
