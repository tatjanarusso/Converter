package com.example.converter.util.converter;

import java.nio.file.Path;

public class Converter {
    protected String pathToOriginal;
    protected String pathToNew;

    public Converter(String original, String newFile){
        pathToOriginal = original;
        pathToNew = newFile;
    }
    public boolean convert(){
        return false;
    }
}
