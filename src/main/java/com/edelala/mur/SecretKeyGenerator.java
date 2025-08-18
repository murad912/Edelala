package com.edelala.mur;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        // Generate a secure random key (e.g., 256 bits for HS256)
        byte[] keyBytes = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded();

        // Base64 encode the key bytes
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("Generated Base64 JWT Secret Key: " + base64Key);
        System.out.println("Copy this key and paste it into your application.properties for 'jwt.secret'");
    }
}