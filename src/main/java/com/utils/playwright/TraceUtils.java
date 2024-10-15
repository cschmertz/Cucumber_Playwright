package com.utils.playwright;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TraceUtils {

    public static void cleanTraces(String traceDirectory) {
        File dir = new File(traceDirectory);
        if (dir.exists() && dir.isDirectory()) {
            File[] traceFiles = dir.listFiles();
            if (traceFiles != null) {
                for (File traceFile : traceFiles) {
                    traceFile.delete();
                }
            }
        }
    }

    public static Path getTraceFilePath(String testName) {
        // Create a directory structure based on test name or timestamp
        String traceDir = "traces/" + testName;
        new File(traceDir).mkdirs();
        return Paths.get(traceDir, "trace.zip");
    }

    public static void deleteOldTraces(String traceDirectory) {
        // Optionally, delete old trace files to prevent disk space overuse
        File dir = new File(traceDirectory);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }
}