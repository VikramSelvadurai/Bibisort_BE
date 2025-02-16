package com.example.bigbisort_be.singnup.buyer_signup.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.aspectj.bridge.IMessage;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BuyerSigninRequestBean {

    @NotBlank(message = "User name must not empty")
    private String userName;
    @NotBlank(message = "Password must not empty")
    private String password;

}
