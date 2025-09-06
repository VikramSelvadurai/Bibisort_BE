package com.example.bigbisort_be.security.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
@Component
@Slf4j
public class EncryptionUtils{

    private static String salt = "7e0c6b34-6a1f-4c98-9fcb-2af1498ea321";
    private static String SECRET_KEY = "Bibisort";

    public static String encryptBase64(String strToEncrypt) {
        return Base64.getEncoder().encodeToString(strToEncrypt.getBytes());
    }

    public static String decryptBase64(String strToEncrypt) {
        return new String(Base64.getDecoder().decode(strToEncrypt));
    }

    public String encrypt(String strToEncrypt) throws Exception {
        // Generate random IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Derive key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 200000, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Encrypt
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));

        // Prepend IV to ciphertext
        byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
        System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(encryptedWithIv);
    }

    public String decrypt(String strToDecrypt) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(strToDecrypt);

        // Extract IV
        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[decoded.length - 16];
        System.arraycopy(decoded, 0, iv, 0, iv.length);
        System.arraycopy(decoded, iv.length, encryptedBytes, 0, encryptedBytes.length);

        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // Derive key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 200000, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Decrypt
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        byte[] decrypted = cipher.doFinal(encryptedBytes);

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
