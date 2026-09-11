# Java Benchmark Test Suite Documentation

Dokumentasi suite pengujian kerentanan keamanan dan performa pada Java (Spring Boot).

## Daftar Test Case

| File | Kategori | Deskripsi Masalah | Tingkat Risiko |
| :--- | :--- | :--- | :--- |
| `UserRepository.java` | Security | SQL injection via string concatenation | High |
| `SystemService.java` | Security | Command injection via `Runtime.getRuntime().exec` | High |
| `FileService.java` | Security | Path traversal via unvalidated file path | High |
| `ProxyService.java` | Security | Server-Side Request Forgery (SSRF) via unvalidated URL | Medium |
| `AuthService.java` | Security | Hardcoded JWT secret key & plain credential logging | High |
| `RedirectController.java` | Security | Open redirect via unvalidated `RedirectView` | Medium |
| `UserController.java` | Security | IDOR endpoint penghapusan user tanpa validasi otentikasi/ownership | High |
| `RegexValidator.java` | Performance | Catastrophic backtracking ReDoS regex pattern | Medium |
| `DocumentService.java` | Performance | Unclosed `InputStream` resource leak tanpa `close()` / try-with-resources | Medium |

## False-Positive Guard Files

| File | Pola Pengujian Guard | Ekspektasi Reviewer |
| :--- | :--- | :--- |
| `SafeGuardController.java` | PreparedStatement (`?`), Whitelist domain redirect, `ProcessBuilder` array args, try-with-resources | **0 False Positives** |
