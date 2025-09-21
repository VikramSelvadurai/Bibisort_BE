package com.example.bigbisort_be.core.admin.sign_up.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminSignupRequestBean {
    private String name;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
}

