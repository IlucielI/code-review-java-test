package com.benchmark.service;

public class TokenSigner {
    // Vulnerable: Hardcoded cryptographic key for signing user sessions (CWE-798, CWE-321)
    private static final String SECRET_SIGNING_KEY = "static_java_benchmark_secret_key_12345";

    public String getSecretKey() {
        return SECRET_SIGNING_KEY;
    }
}
