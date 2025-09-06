package com.example.bigbisort_be.security.constants;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtConstants {
    public static final String REFRESH_TOKEN_ROLE = "REFRESH_TOKEN";
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private JwtConstants() {
        throw new UnsupportedOperationException();
    }

    public static class ApiResponseFields {
        public static final String USER_ID = "userId";
        public static final String USER_NAME = "userName";
        public static final String AUTH_TYPE = "IN_MEMORY";
        public static final String ROLES = "roles";
        private ApiResponseFields() {}
    }
}
