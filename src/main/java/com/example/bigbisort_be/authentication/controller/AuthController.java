package com.example.bigbisort_be.authentication.controller;

import com.example.bigbisort_be.authentication.bean.LoginRequestBean;
import com.example.bigbisort_be.security.core.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestBean loginRequestBean) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBean.getUsername(), loginRequestBean.getPassword()));
            return jwtUtil.generateToken(loginRequestBean.getUsername());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
