package com.example.bigbisort_be.security.core.twilio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
@Service
public class TwilioVerifyService {
    @Value("${twilio.verify-service-sid}")
    private String verifyServiceSid;

    /**
     * Send a verification code to the given phone number via the specified channel.
     * channel: "sms", "call", or "whatsapp"
     */
    public Verification sendVerification(String phoneNumber, String channel) {
        return Verification.creator(
                verifyServiceSid,
                phoneNumber,
                channel
        ).create();
    }

    /**
     * Check the code returned by the user.
     * Returns status "approved" when valid.
     */
    public VerificationCheck checkVerification(String phoneNumber, String code) {
        return VerificationCheck.creator(verifyServiceSid)
                .setTo(phoneNumber)
                .setCode(code)
                .create();
    }
}
