package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Ascifier {

    public void ascify(final BufferedImage grayImage) throws IOException {
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        // Loop through each pixel
        final List<Character> textList = new ArrayList<>();
        final int charWidth = 4;
        final int charHeight = 8;
        final int horizontalCt = width/charWidth;
        final int verticalCt = height/charHeight;
        for (int y = 0; y < verticalCt; y++) {
            for (int x = 0; x < horizontalCt; x++) {
                final TextPix textPix = new TextPix(charHeight,charWidth,new ArrayList<>());
                textPix.populate(grayImage,x,y);
                textList.add(textPix.mapPix());
            }
            textList.add('\n');
        }

        final String text = textList.stream().map(String::valueOf).collect(Collectors.joining());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            System.out.println( text );
//            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
