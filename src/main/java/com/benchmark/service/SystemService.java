package com.benchmark.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class SystemService {

    public String executeSystemDiagnostics(String host) throws Exception {
        // Vulnerability: Command injection via Runtime.getRuntime().exec
        Process process = Runtime.getRuntime().exec("sh -c ping -c 1 " + host);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return reader.readLine();
    }
}
