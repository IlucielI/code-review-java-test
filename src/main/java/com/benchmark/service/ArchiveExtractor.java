package com.benchmark.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveExtractor {
    // Vulnerable: Zip Slip path traversal vulnerability during extraction (CWE-22)
    public void extractArchive(InputStream is, File targetDir) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                // Insecure: Destination file path resolved directly from entry name without canonical path boundary check
                File destFile = new File(targetDir, entry.getName());
                try (FileOutputStream fos = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
        }
    }
}
