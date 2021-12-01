package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service;

import java.awt.image.BufferedImage;

public class PpmReader {

    static public BufferedImage ppm(int width, int height, byte[] data) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int r, g, b, k = 0, pixel;
        for (int y = 0; y < height; y++) {
            for (int x = 0; (x < width) && ((k + 3) < data.length); x++) {
                r = data[k++] & 0xFF;
                g = data[k++] & 0xFF;
                b = data[k++] & 0xFF;
                pixel = 0xFF000000 + (r << 16) + (g << 8) + b;
                image.setRGB(x, y, pixel);
            }
        }
        return image;
    }

}
