package org.example;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
public class GrayScaler {
    public enum Color {
        RED,
        GREEN,
        BLUE
    }

@NonNull
    private final InputStream fileStream;
    private int extractRGB(int rgb,Color color){
        int lsb = 0xff;
        switch (color){
            case BLUE -> {
                return  rgb &lsb;
            }
            case GREEN -> {
                return (rgb >> 8) & lsb;
            }

            default -> {
                return (rgb >> 16) & lsb;
            }
        }
    }
    private int computeGrayscale(final int rgb){

        int red   = this.extractRGB(rgb,Color.RED);
        int green = this.extractRGB(rgb,Color.GREEN);
        int blue  = this.extractRGB(rgb,Color.BLUE);

        // Compute luminance using standard Rec. 601 coefficients
        int gray = (int)(0.299 * red + 0.587 * green + 0.114 * blue);

        // Pack gray into RGB format (R=G=B=gray)
        return (gray << 16) | (gray << 8) | gray;

    }
    public void greyScale() throws IOException {
        BufferedImage colorImage = ImageIO.read(this.fileStream);
        int width = colorImage.getWidth();
        int height = colorImage.getHeight();

        // Create a grayscale image with same dimensions
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Loop through each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = colorImage.getRGB(x, y);
               final int grayColor = this.computeGrayscale(rgb);
                grayImage.setRGB(x, y, grayColor);
            }
        }

        // Save output (optional)
        File output = new File("grayscale_output.jpg");
        ImageIO.write(grayImage, "jpg", output);
        System.out.println("Saved grayscale image to: " + output.getAbsolutePath());
    }
}
