package com.example.converter.util.converter;

import java.io.File;
import java.io.IOException;

public class Converter {
    protected String pathToOriginal;
    protected String pathToNew;
    protected File originalFile;
    protected File newFile;

    public Converter(String original, String destination){
        pathToOriginal = original;
        pathToNew = destination;
        originalFile = new File ( original );
        newFile = new File (destination);
    }
    public boolean convert() throws IOException {
        return false;
    }
}
