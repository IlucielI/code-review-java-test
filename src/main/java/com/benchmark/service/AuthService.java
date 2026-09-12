package com.benchmark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    // Vulnerability: Hardcoded JWT secret key
    private static final String JWT_SECRET = "my_super_secret_jwt_token_key_12345";

    public boolean authenticate(String username, String password) {
        // Vulnerability: Plaintext credential logging
        logger.info("Authentication attempt for user=" + username + ", password=" + password);
        return "admin".equals(username) && "secret".equals(password);
    }
}
