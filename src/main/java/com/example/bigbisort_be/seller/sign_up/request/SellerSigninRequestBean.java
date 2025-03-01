package com.example.bigbisort_be.seller.sign_up.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerSigninRequestBean {

    @NotBlank(message = "Phone must not empty")
    private String phoneNumber;
    @NotBlank(message = "Username must not empty")
    private String userName;


}
