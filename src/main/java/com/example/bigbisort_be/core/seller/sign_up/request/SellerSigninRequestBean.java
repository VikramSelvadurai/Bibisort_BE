package com.example.bigbisort_be.core.seller.sign_up.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerSigninRequestBean {

    private String mobileNumber;
    private String countryCode;
    private String otp;


}
