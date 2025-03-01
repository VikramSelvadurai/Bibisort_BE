package com.example.bigbisort_be.buyer.sign_up.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BuyerSignupResponseBean extends RepresentationModel<BuyerSignupResponseBean> {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
}
