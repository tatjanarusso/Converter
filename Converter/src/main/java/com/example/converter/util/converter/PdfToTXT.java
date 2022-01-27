package com.example.converter.util.converter;

import org.apache.pdfbox.pdfparser.PDFParser;

import java.io.File;

public class PdfToTXT extends Converter{
    public PdfToTXT(String original, String newFile){
        super(original, newFile);
    }

    public boolean convert(){
        File currentFile = new File(pathToOriginal);
        String convertedText = "";
        PDFParser parser = null;
        return false;
    }
}
