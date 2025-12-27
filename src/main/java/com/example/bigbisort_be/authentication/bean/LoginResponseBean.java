package com.example.bigbisort_be.authentication.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseBean {
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
    private String message;
}
