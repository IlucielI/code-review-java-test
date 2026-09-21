package com.benchmark.repository;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class UserRepository {

    public String findUserByUsername(String username) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:app.db");
        Statement stmt = conn.createStatement();
        // Vulnerability: SQL injection via string concatenation
        String query = "SELECT id, username, role FROM users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(query);
        String result = rs.next() ? rs.getString("username") : null;
        conn.close();
        return result;
    }
}
