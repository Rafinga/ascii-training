package org.example;

import lombok.Getter;
import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.util.List;

@Getter
public class TextPix {

    public enum CharacterSet {
        DEFAULT("█▓▒░$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. "),
        GRADIENT("█▇▆▅▄▃▂▁▔▏▎▍▌▋▊▉▓▒░ "),
        EXTENDED("██▓▒░▉▊▋▌▍▎▏▐▀▄█▇▆▅▄▃▂▁$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ");
        
        private final String chars;
        
        CharacterSet(String chars) {
            this.chars = chars;
        }
        
        public String getChars() {
            return chars;
        }
    }

    private final int height;
    private final int width;
    @NonNull
    private final List<Integer> pixels;
    private final CharacterSet characterSet;

    public TextPix(int height, int width, List<Integer> pixels, CharacterSet characterSet) {
        this.height = height;
        this.width = width;
        this.pixels = pixels;
        this.characterSet = characterSet;
    }

    private boolean isPopulated = false;

    public void populate(final BufferedImage image, final int x, final int y){
        final int scaledX = width *x;
        final int scaledY = height * y;

        for (int deltaY = 0; deltaY < height; deltaY++){
            for (int deltaX = 0; deltaX < width; deltaX++){
                final int selectedX = scaledX + deltaX;
                final int selectedY = scaledY + deltaY;
                int rgb = image.getRGB(selectedX, selectedY);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int luminance = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
                pixels.add(luminance);
            }
        }
        isPopulated = true;
    }

    private char sampleCharacter(int intensity){
        if (intensity<0 || intensity >255){
            throw new RuntimeException("image not in bounds!");
        }
        final String replacementMap = characterSet.getChars();
        final int sampleIndex = ((255-intensity) * replacementMap.length()) / 256;
        return replacementMap.charAt(Math.min(sampleIndex, replacementMap.length()-1));
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
