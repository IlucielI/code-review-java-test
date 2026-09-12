package com.benchmark.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;

@Service
public class FileService {

    private static final String BASE_DIR = "/var/app/data";

    public byte[] getFileContent(String filename) throws Exception {
        // Vulnerability: Path traversal via unvalidated user path
        File targetFile = new File(BASE_DIR + "/" + filename);
        try (FileInputStream fis = new FileInputStream(targetFile)) {
            return fis.readAllBytes();
        }
    }
}
