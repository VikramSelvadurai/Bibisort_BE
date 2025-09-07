package com.example.bigbisort_be.security.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final String SECRET_KEY_GEN_KEY ="zjYeLRusmijLxsJZBysd7bHQeoR30uP9";
    private static final long EXPIRATION_MS = 1000 * 30; // 30min

    private final SecretKey key;

    public JwtUtils() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY_GEN_KEY.getBytes());;
    }

    public String generateToken(String username, List<String> list) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .claim("Roles",list)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)// 🔹 same key used here
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)  // 🔹 and the same key used here
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Date getExpiration(String token) {
        return Jwts.parser()
                .verifyWith(key)  // 🔹 and the same key used here
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public List<String> extractRoles(String token) {
        Object roles = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles");

        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .map(Object::toString)
                    .toList();
        }
        return Collections.emptyList();
    }

    public String generateRefreshToken(String username, List<String> list) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .claim("Roles",list)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
