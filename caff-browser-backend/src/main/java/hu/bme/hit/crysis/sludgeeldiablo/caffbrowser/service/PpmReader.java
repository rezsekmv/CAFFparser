package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service;

import java.awt.image.BufferedImage;

public class PpmReader {

    private PpmReader() {
        throw new IllegalStateException("Utility class");
    }

    public static BufferedImage ppm(int width, int height, byte[] data) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int k = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; (x < width) && ((k + 3) < data.length); x++) {
                int r = data[k++] & 0xFF;
                int g = data[k++] & 0xFF;
                int b = data[k++] & 0xFF;
                int pixel = 0xFF000000 + (r << 16) + (g << 8) + b;
                image.setRGB(x, y, pixel);
            }
        }
        return image;
    }

}
