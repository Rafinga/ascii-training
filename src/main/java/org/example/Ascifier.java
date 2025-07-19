package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Ascifier {


    private char sampleCharacter(int intensity){
        if (intensity<0 || intensity >255){
            throw new RuntimeException("image not in bounds!");
        }
        final String replacementMap = " .,:;ox%#@";
        final int sampleIndex =((255-intensity)*10)/256;
        return replacementMap.charAt(sampleIndex);
    }


    public void ascify(InputStream imageStream) throws IOException {
        final GrayScaler grayScaler = new GrayScaler(imageStream);
        grayScaler.greyScale();


        final String path = "grayscale_output.jpg";
        BufferedImage grayImage = ImageIO.read(new File(path));
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        // Loop through each pixel
        final List<Character> textList = new ArrayList<>();
        final int charWidth = 100;
        final int charHeight = 200;
        final int horizontalCt = width/charWidth;
        final int verticalCt = height/charHeight;
        for (int y = 0; y < verticalCt; y++) {
            for (int x = 0; x < horizontalCt; x++) {
                final TextPix textPix = new TextPix(charHeight,charWidth,new ArrayList<>());
                textPix.populate(grayImage,x,y);
                textList.add(textPix.mapPix());
//                System.out.printf( "selectedX:%d selectedY:%d\n",x,y );
            }



            textList.add('\n');
        }

        final String text = textList.stream().map(String::valueOf).collect(Collectors.joining());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
