package com.example.converter.util.converter;

import org.apache.pdfbox.pdfparser.PDFParser;

import java.io.File;
import java.io.RandomAccessFile;

public class PdfToTXT extends Converter{
    public PdfToTXT(String original, String newFile){
        super(original, newFile);
    }

    public boolean convert(){
        File currentFile = new File(pathToOriginal);
        String convertedText = "";
        PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();
        return false;
    }
}
