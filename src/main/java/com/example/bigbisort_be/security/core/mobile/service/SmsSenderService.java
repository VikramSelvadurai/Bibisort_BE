//package com.example.bigbisort_be.security.core.mobile.service;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//
//import jakarta.annotation.PostConstruct;   // or javax.annotation.PostConstruct if using older Java EE
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SmsSenderService {
//
//    @Value("${twilio.accountSid}")
//    private String accountSid;
//
//    @Value("${twilio.authToken}")
//    private String authToken;
//
//    @Value("${twilio.phoneNumber}")
//    private String fromNumber;
//
//    @PostConstruct
//    public void initTwilio() {
//        Twilio.init(accountSid, authToken);
//    }
//
//    public void sendOtp(String toPhoneNumber, String code) {
//        Message.creator(
//                new PhoneNumber(toPhoneNumber),
//                new PhoneNumber(fromNumber),
//                "Your OTP code is: " + code
//        ).create();
//    }
//}
