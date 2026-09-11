package com.benchmark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public RedirectView redirectUser(@RequestParam("url") String url) {
        // Vulnerability: Open redirect without domain validation
        return new RedirectView(url);
    }
}
