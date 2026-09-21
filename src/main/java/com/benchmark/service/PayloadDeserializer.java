package com.benchmark.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class PayloadDeserializer {
    // Vulnerable: Insecure Java deserialization via ObjectInputStream (CWE-502)
    public Object deserialize(byte[] data) throws Exception {
        // Insecure: Untrusted byte array deserialized without ClassFilter allowlist
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        }
    }
}
