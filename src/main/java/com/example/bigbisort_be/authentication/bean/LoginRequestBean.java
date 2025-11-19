package com.example.bigbisort_be.authentication.bean;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequestBean {
    /**
     * login user name
     */
    private String username;
    private String password;
    private AuthenticationType authenticationType;
}
