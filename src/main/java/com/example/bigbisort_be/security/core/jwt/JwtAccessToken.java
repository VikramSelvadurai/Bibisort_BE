package com.example.bigbisort_be.security.core.jwt;

import com.example.bigbisort_be.security.core.Exception.JwtTokenExpiredException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.Serial;

public class JwtAccessToken implements JwtToken{

    @Serial
    private static final long serialVersionUID = 4727871844167217888L;

    private static Logger logger = LoggerFactory.getLogger(JwtAccessToken.class);

    @Getter
    @Setter
    private String token;

    public JwtAccessToken(String token) {
        this.token = token;
    }

    /** Parses and validates JWT Token signature. */
//    public Jws<Claims> parseClaims(String signingKey) {
//        try {
//            return Jwts.parser().setSigningKey(signingKey.getBytes()).parseClaimsJws(this.token);
//        } catch (SignatureException
//                 | UnsupportedJwtException
//                 | MalformedJwtException
//                 | IllegalArgumentException ex) {
//            logger.error("Token invalid", ex);
//            throw new BadCredentialsException("Token invalid", ex);
//        } catch (ExpiredJwtException expiredEx) {
//            logger.info("Token invalid", expiredEx);
//            throw new JwtTokenExpiredException(this, "Token Expired", expiredEx);
//        }
//    }
}
