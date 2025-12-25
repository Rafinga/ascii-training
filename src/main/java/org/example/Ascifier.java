package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;

public class Ascifier {

    private final TextPix.CharacterSet charset;
    private final int blockWidth;
    private final int blockHeight;
    
    public Ascifier(TextPix.CharacterSet charset, int blockWidth, int blockHeight) {
        this.charset = charset;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
    }

    public void ascify(final BufferedImage grayImage) throws IOException {
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        final int charWidth = blockWidth;
        final int charHeight = blockHeight;
        final int horizontalCt = width/charWidth;
        final int verticalCt = height/charHeight;
        
        final Character[] textArray = new Character[verticalCt * (horizontalCt + 1)]; // +1 for newlines
        
        IntStream.range(0, verticalCt).parallel().forEach(y -> {
            IntStream.range(0, horizontalCt).parallel().forEach(x -> {
                final TextPix textPix = new TextPix(charHeight, charWidth, new ArrayList<>(), charset);
                textPix.populate(grayImage, x, y);
                textArray[y * (horizontalCt + 1) + x] = textPix.mapPix();
            });
            textArray[y * (horizontalCt + 1) + horizontalCt] = '\n';
        });
        
        final List<Character> textList = Arrays.asList(textArray);
        final String text = textList.stream().map(String::valueOf).collect(Collectors.joining());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            System.out.println( text );
//            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
