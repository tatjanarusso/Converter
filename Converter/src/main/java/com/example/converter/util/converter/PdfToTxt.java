package com.example.converter.util.converter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.PrintWriter;

public class PdfToTxt extends Converter{
    public PdfToTxt(String original, String newFile){super(original, newFile);}

        public boolean convert() throws IOException {
            String parsedText;
            PDFParser parser = new PDFParser(new RandomAccessFile (originalFile, "r")); // r = Read only
            parser.parse();

            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);

            PrintWriter pw = new PrintWriter(pathToNew);
            pw.print(parsedText);
            pw.close();

            return newFile.exists();
        }
}
