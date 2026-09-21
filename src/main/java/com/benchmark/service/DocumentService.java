package com.benchmark.service;

import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DocumentService {

    public int countBytes(String path) throws Exception {
        // Performance / Resource Leak: Stream opened without close() or try-with-resources
        InputStream in = new FileInputStream(path);
        int total = 0;
        while (in.read() != -1) {
            total++;
        }
        return total;
    }
}
