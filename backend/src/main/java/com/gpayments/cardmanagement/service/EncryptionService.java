package com.gpayments.cardmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class EncryptionService {
    
    @Value("${app.encryption.secret-key}")
    private String secretKey;
    
    private final AesBytesEncryptor encryptor;
    
    public EncryptionService(@Value("${app.encryption.secret-key}") String secretKey) {
        // AesBytesEncryptor constructor: (password, salt)
        // Using a fixed salt for consistent encryption/decryption
        this.encryptor = new AesBytesEncryptor(secretKey, "card-management-salt");
    }
    
    public String encrypt(String plaintext) throws Exception {
        if (plaintext == null) {
            throw new IllegalArgumentException("Plaintext cannot be null");
        }
        
        // Spring handles all the low-level crypto operations internally
        byte[] encrypted = encryptor.encrypt(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public String decrypt(String encryptedData) throws Exception {
        if (encryptedData == null) {
            throw new IllegalArgumentException("Encrypted data cannot be null");
        }
        
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = encryptor.decrypt(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
    
    public String generateHash(String pan) throws Exception {
        if (pan == null) {
            throw new IllegalArgumentException("PAN cannot be null");
        }
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pan.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
    
    public String getLastFourDigits(String pan) {
        if (pan == null) {
            return "";
        }
        
        if (pan.length() >= 4) {
            return pan.substring(pan.length() - 4);
        }
        return pan;
    }
    
    public String maskPan(String pan) {
        if (pan == null) {
            return "";
        }
        
        if (pan.length() <= 4) {
            return pan;
        }
        return "*".repeat(pan.length() - 4) + pan.substring(pan.length() - 4);
    }
}


