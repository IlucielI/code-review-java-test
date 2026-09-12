package com.benchmark.service;

import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ProxyService {

    public String fetchRemoteContent(String targetUrl) throws Exception {
        // Vulnerability: Server-Side Request Forgery (SSRF) without url validation
        URL url = new URL(targetUrl);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        try (InputStream in = conn.getInputStream()) {
            return new String(in.readAllBytes());
        }
    }
}
