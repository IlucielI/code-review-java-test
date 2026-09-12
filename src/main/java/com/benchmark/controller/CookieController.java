package com.benchmark.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieController {
    // Insecure cookie: setting auth token cookie with HttpOnly=false and Secure=false
    @PostMapping("/api/auth/cookie")
    public String setAuthCookie(HttpServletResponse response, @RequestParam String token) {
        Cookie cookie = new Cookie("auth_session", token);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        response.addCookie(cookie);
        return "cookie-set";
    }
}
