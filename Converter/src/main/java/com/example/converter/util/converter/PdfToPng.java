package com.example.converter.util.converter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PdfToPng extends Converter{
    public PdfToPng(String original, String newFile){
        super(original, newFile);
    }

    public boolean convert() throws IOException {
        if (originalFile.exists()) {
            PDDocument document = PDDocument.load ( originalFile );
            PDFRenderer pdfRenderer = new PDFRenderer ( document );

            for (int pageNumber = 0; pageNumber < document.getNumberOfPages (); ++pageNumber) {
                BufferedImage bim = pdfRenderer.renderImage ( pageNumber );

                String destDir = pathToNew + "_" + pageNumber + ".png";

                ImageIO.write ( bim, "png", new File ( destDir ) );
            }
            document.close ();

        }
        File imageFile = new File(pathToNew + "_1" + ".png");
        return imageFile.exists();

    }
}
