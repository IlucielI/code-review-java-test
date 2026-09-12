package com.benchmark.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    // Missing rate limiting on sensitive authentication route
    @PostMapping("/api/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return "logged-in:" + username;
    }
}
