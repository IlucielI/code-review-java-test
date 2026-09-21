package com.benchmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class UserController {

    private final Map<Long, String> userDatabase = new ConcurrentHashMap<>();

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        // Vulnerability: IDOR without authentication or ownership check
        userDatabase.remove(id);
        return ResponseEntity.ok(Map.of("status", "deleted", "userId", id));
    }
}
