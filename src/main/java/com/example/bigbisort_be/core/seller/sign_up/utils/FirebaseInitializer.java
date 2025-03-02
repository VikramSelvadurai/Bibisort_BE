package com.example.bigbisort_be.core.seller.sign_up.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitializer {

    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("path/to/firebase-service-account.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        System.out.println("Firebase Initialized");
    }
}
