package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@RestController
public class VulnerableController {

    // Bug 1: SQL Injection via Statement
    @GetMapping("/api/users/search")
    public List<String> searchUser(@RequestParam String name, Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE name = '" + name + "'");
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("name"));
        }
        return list;
    }

    // Bug 2: Unsafe Runtime Exec Command Injection
    @PostMapping("/api/ping")
    public String pingHost(@RequestParam String host) throws IOException {
        Process p = Runtime.getRuntime().exec("ping -c 1 " + host);
        return "Ping dispatched";
    }

    // Bug 3: Unclosed InputStream Resource Leak
    @GetMapping("/api/file")
    public String readFileContent(@RequestParam String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        return new String(data); // Unclosed stream leaks file descriptor
    }

    // Safe Guard: PreparedStatement binding (MUST NOT be flagged as SQLi)
    @GetMapping("/api/users/search-safe")
    public List<String> searchUserSafe(@RequestParam String name, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT name FROM users WHERE name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("name"));
        }
        rs.close();
        ps.close();
        return list;
    }
}
