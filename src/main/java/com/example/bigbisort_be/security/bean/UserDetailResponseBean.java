package com.example.bigbisort_be.security.bean;

import com.example.bigbisort_be.common.bean.UserAuthenticationDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponseBean {
    private UserDetails userDetails;
    private UserAuthenticationDetails authenticationDetails;
}
