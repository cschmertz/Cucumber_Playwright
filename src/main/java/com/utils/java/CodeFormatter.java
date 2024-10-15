package com.utils.java;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeFormatter {

    public static void formatFile(String filePath) throws IOException, FormatterException {
        Path path = Paths.get(filePath);
        String content = new String(Files.readAllBytes(path));
        String formattedContent = new Formatter().formatSource(content);
        Files.write(path, formattedContent.getBytes());
    }

    public static void formatDirectory(String directoryPath) throws IOException, FormatterException {
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {
                    try {
                        formatFile(path.toString());
                    } catch (IOException | FormatterException e) {
                        e.printStackTrace();
                    }
                });
    }
}
