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

        System.out.println(sampleIndex);
        return replacementMap.charAt(sampleIndex);
    }


    public void ascify(InputStream imageStream) throws IOException {
        final GrayScaler grayScaler = new GrayScaler(imageStream);
        grayScaler.greyScale();


        final String path = "grayscale_output.jpg";
        final Compresser compresser = new Compresser(path);
        final String compressedPath = "resized_output.jpg";
        compresser.compress((double) 1 /(1<<1), (double) 1 /(1<<2));
        BufferedImage grayImage = ImageIO.read(new File(compressedPath));
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        // Loop through each pixel
        final List<Character> textList = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int intensity = grayImage.getRGB(x, y) &0xFF;
                final char ascii = this.sampleCharacter(intensity);
                textList.add(ascii);
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
