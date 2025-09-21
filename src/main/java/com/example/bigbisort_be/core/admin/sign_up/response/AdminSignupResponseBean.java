package com.example.bigbisort_be.core.admin.sign_up.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminSignupResponseBean extends RepresentationModel<AdminSignupResponseBean> {
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
}
