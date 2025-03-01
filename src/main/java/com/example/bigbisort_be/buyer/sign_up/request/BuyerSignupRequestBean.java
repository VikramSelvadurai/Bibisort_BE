package com.example.bigbisort_be.buyer.sign_up.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BuyerSignupRequestBean {
    private String name;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String password;

}
