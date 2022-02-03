package com.example.converter.util.converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PngToJgp extends Converter{
    public PngToJgp(String original, String newFile) {
        super ( original, newFile );
    }

    public boolean convert() throws IOException {
        BufferedImage img = ImageIO.read(originalFile);
        ImageIO.write(img, "jpg", newFile);

        return newFile.exists();
    }
}
