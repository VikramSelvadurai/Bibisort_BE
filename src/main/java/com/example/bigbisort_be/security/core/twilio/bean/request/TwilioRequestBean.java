package com.example.bigbisort_be.security.core.twilio.bean.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TwilioRequestBean {
    private String phoneNumber;
    private String channel;
}
