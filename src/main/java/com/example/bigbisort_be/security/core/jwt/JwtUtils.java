package com.example.bigbisort_be.security.core.jwt;

import com.google.api.services.storage.Storage;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class JwtUtils {

    private static final String SECRET_KEY_GEN_KEY ="zjYeLRusmijLxsJZBysd7bHQeoR30uP9";
//    private static final SecretKey SECRET_KEY =



    public static SecretKey generateSecretKey() throws Exception {
        // Convert the string key to bytes
        byte[] keyBytes = SECRET_KEY_GEN_KEY.getBytes(StandardCharsets.UTF_8);

        // Hash it to ensure it is the right length (AES key length)
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);

        // Use only first 16 bytes for AES-128
        keyBytes = Arrays.copyOf(keyBytes, 16);

        // Generate SecretKey
        return new SecretKeySpec(keyBytes, "AES");
    }

    public void generateToken(String userName) {


    }

}
