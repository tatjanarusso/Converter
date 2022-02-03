package com.example.converter.util.converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFolder extends Converter {
    public ZipFolder(String original, String newFile) {
        super ( original, newFile );
    }

    public boolean convert() throws IOException {
        Path p = Files.createFile( Paths.get(pathToNew));
        try (ZipOutputStream zipStream = new ZipOutputStream(Files.newOutputStream(p))) {
            Path originalPath = newFile.toPath ();
            Files.walk(originalPath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                try {
                    ZipEntry zipEntry = new ZipEntry ( originalPath.relativize ( path ).toString () );
                    zipStream.putNextEntry ( zipEntry );
                    Files.copy ( path, zipStream );
                    zipStream.closeEntry ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            });
        }
        return newFile.exists();
    }
}