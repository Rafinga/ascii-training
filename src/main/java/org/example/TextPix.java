package org.example;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class TextPix {

    private final int height;
    private  final int width;
    @NonNull
    private final List<Integer> pixels;

    private boolean isPopulated = false;

    public void populate(final BufferedImage image, final int x, final int y){
        final int scaledX = width *x;
        final int scaledY = height * y;


        for (int deltaY = 0; deltaY < height; deltaY++){
            for (int deltaX = 0; deltaX < width; deltaX++){
                final int selectedX = scaledX + deltaX;
                final int selectedY = scaledY + deltaY;
                pixels.add(image.getRGB(selectedX, selectedY) &0xFF);

            }
        }
        isPopulated = true;

    }

    private char sampleCharacter(int intensity){
        if (intensity<0 || intensity >255){
            throw new RuntimeException("image not in bounds!");
        }
        final String replacementMap = " .,:;ox%#@";
        final int sampleIndex =((255-intensity)*10)/256;
        return replacementMap.charAt(sampleIndex);
    }

    public char mapPix(){
        if (!isPopulated){
            throw new RuntimeException("not populated");
        }
        final int numPix = height *width;
        final int colorSum = pixels.stream().reduce(0, Integer::sum);
        final int avgColor = colorSum/numPix;
        return sampleCharacter(avgColor);
    }




}
