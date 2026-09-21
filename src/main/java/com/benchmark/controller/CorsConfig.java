package com.benchmark.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class CorsConfig {
    @GetMapping("/api/cors/data")
    public String getData() {
        return "sensitive-user-payload";
    }
}
