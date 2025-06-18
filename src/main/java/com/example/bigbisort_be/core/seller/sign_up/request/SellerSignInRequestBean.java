package com.example.bigbisort_be.core.seller.sign_up.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerSignInRequestBean {

    private String mobileNumber;
    private String countryCode;
    private String otp;

}
