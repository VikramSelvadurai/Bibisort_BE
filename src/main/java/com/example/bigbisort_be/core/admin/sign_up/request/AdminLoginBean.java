package com.example.bigbisort_be.core.admin.sign_up.request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminLoginBean {
    private String userName;
    private String password;
    private String authenticationType;
}
