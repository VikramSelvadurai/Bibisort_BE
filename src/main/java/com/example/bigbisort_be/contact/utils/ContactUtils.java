package com.example.bigbisort_be.contact.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;
@Component
public class ContactUtils {

    public String encodeBase64(byte[] strToEncrypt) {
        return Base64.getEncoder().encodeToString(strToEncrypt);
    }
}
