package com.example.bigbisort_be.security.core.jwt;

import java.io.Serializable;
@FunctionalInterface
public interface JwtToken extends Serializable {
    String getToken();
}
