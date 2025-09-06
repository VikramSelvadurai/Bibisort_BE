package com.example.bigbisort_be.security.core.Exception;

import com.example.bigbisort_be.security.core.jwt.JwtAccessToken;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class JwtTokenExpiredException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtAccessToken token;

    public JwtTokenExpiredException(String msg) {
        super(msg);
    }

    public JwtTokenExpiredException(JwtAccessToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
