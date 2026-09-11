package com.benchmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class SafeGuardController {

    private static final Set<String> ALLOWED_DOMAINS = Set.of("example.com", "api.example.com");

    @GetMapping("/safe/user")
    public ResponseEntity<?> safeUserQuery(@RequestParam("username") String username) throws Exception {
        // Guard: PreparedStatement with bind variable (?) - NOT SQL injection
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
             PreparedStatement ps = conn.prepareStatement("SELECT id, username FROM users WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return ResponseEntity.ok(rs.next() ? rs.getString("username") : "not found");
            }
        }
    }

    @GetMapping("/safe/redirect")
    public RedirectView safeRedirect(@RequestParam("url") String url) {
        // Guard: Whitelist domain validation - NOT open redirect
        for (String allowed : ALLOWED_DOMAINS) {
            if (url.startsWith("https://" + allowed + "/")) {
                return new RedirectView(url);
            }
        }
        return new RedirectView("/dashboard");
    }

    @GetMapping("/safe/file")
    public Map<String, Object> safeFileRead() throws Exception {
        // Guard: Try-with-resources automatic closing - NOT resource leak
        File file = new File("/var/app/data/safe.txt");
        if (!file.exists()) {
            return Map.of("status", "file_not_found");
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            return Map.of("size", fis.available());
        }
    }

    public List<String> safeCommandExecution() throws Exception {
        // Guard: ProcessBuilder with parameterized argument list - NOT command injection
        ProcessBuilder pb = new ProcessBuilder("ls", "-la");
        Process p = pb.start();
        return List.of("status", String.valueOf(p.waitFor()));
    }
}
