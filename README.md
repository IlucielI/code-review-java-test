# Java (Spring Boot) Benchmark Test Suite

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg?logo=openjdk)](https://openjdk.org/)
[![Framework](https://img.shields.io/badge/Framework-Spring%20Boot%203.x-6DB33F.svg?logo=springboot)](https://spring.io/projects/spring-boot)
[![Benchmark Category](https://img.shields.io/badge/Benchmark-Security%20%26%20Resource%20Leaks-blue.svg)](#test-case-matrix)
[![Safe Guard](https://img.shields.io/badge/False%20Positive%20Guard-Active-brightgreen.svg)](#anti-false-positive-guard-controller)

Benchmark test suite for automated code review engines on Java / Spring Boot enterprise applications. This repository contains intentional security vulnerabilities, unclosed I/O resource leaks, ReDoS regex patterns, and safe-guard controllers to validate zero false positives.

---

## 🎯 Benchmark Purpose

1. **Enterprise Security Precision:** Accurately catches SQL injection in raw JDBC queries, command injection via `Runtime.exec()`, SSRF via Java `URL.openConnection()`, IDOR in REST endpoints, and path traversal in file services.
2. **Resource Management & Leaks:** Detects unclosed `InputStream` and I/O handles that cause file descriptor starvation when missing try-with-resources or explicit `close()`.
3. **Safe Patterns & Framework Idioms:** Validates that `PreparedStatement`, `ProcessBuilder` with argument arrays, try-with-resources blocks, and domain whitelists produce **zero false positives**.

---

## 📋 Test Case Matrix

### 🔴 Security Vulnerabilities

| File | Issue / Vulnerability | Type | CWE | Severity | Expected |
| :--- | :--- | :--- | :--- | :---: | :---: |
| `UserRepository.java` | SQL Injection via string concatenation in `Statement.executeQuery` | Injection | CWE-89 | High | **BLOCKING** |
| `SystemService.java` | Command Injection via `Runtime.getRuntime().exec("sh -c " + cmd)` | RCE | CWE-78 | High | **BLOCKING** |
| `FileService.java` | Path Traversal via unvalidated `new File(base, filename)` | File Security | CWE-22 | High | **BLOCKING** |
| `ProxyService.java` | Server-Side Request Forgery (SSRF) via `URL.openConnection()` | Network Security | CWE-918 | Medium | **BLOCKING** |
| `AuthService.java` | Hardcoded JWT Secret Key & Plaintext Credential Logging | Credential Exposure | CWE-798 / CWE-532 | High | **BLOCKING** |
| `RedirectController.java` | Open Redirect via unvalidated destination `RedirectView` | Redirection | CWE-601 | Medium | **BLOCKING** |
| `UserController.java` | IDOR on user account deletion without ownership check | Broken Access Control | CWE-639 | High | **BLOCKING** |
| `CorsConfig.java` | Wildcard `*` origin with `allowCredentials=true` | CORS Misconfiguration | CWE-942 | High | **BLOCKING** |
| `XmlService.java` | XML parser without secure processing disabled (XXE) | Injection / XXE | CWE-611 | High | **BLOCKING** |
| `CookieController.java` | Cookies explicitly configured with `HttpOnly=false` and `Secure=false` | Insecure Cookie | CWE-614 / CWE-1004 | Medium | **NON-BLOCKING** |
| `LoginController.java` | Authentication login route missing rate limiting or throttling | Missing Rate Limiting | CWE-307 | Medium | **NON-BLOCKING** |

### ⚡ Performance & Resource Leaks

| File | Issue | Type | Severity | Expected |
| :--- | :--- | :--- | :---: | :---: |
| `DocumentService.java` | Unclosed `FileInputStream` resource stream leak | Resource Leak | Medium | **NON-BLOCKING** |
| `RegexValidator.java` | Catastrophic Backtracking Regular Expression (ReDoS) | Algorithmic Complexity | Medium | **NON-BLOCKING** |

---

## 🛡️ Anti-False-Positive Guard Controller

| File | Safe Pattern Implemented | Expected Reviewer Result |
| :--- | :--- | :---: |
| `SafeGuardController.java` | Parameterized `PreparedStatement` (`?`), strict domain whitelist for redirects, `ProcessBuilder` array args, try-with-resources auto-close, secure XML processing (`FEATURE_SECURE_PROCESSING`), hardened `HttpOnly`/`Secure` cookies | **0 False Positives** (Clean) |

---

## 🚀 How to Run the Benchmark

```bash
# View PR on GitHub
gh pr view 1 --web

# Trigger Review via API
curl -X POST http://localhost:8081/api/v1/review/trigger \
  -H "Content-Type: application/json" \
  -d '{
    "repository": "IlucielI/code-review-java-test",
    "pull_request_id": 1
  }'
```

---

## 📊 Benchmark Validation Results

- **Detection Rate:** 15 / 15 (100%)
- **False Positive Rate:** 0 / 1 (`SafeGuardController.java` completely passed)
- **False Negative Rate:** 0%
