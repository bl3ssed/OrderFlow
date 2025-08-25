package com.blessed.itemservice.util;

import com.blessed.itemservice.model.dto.UserInfo;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public UserInfo extractUserInfo(String token) {
        Claims claims = extractAllClaims(token);
        return new UserInfo(
                claims.get("id", Long.class),
                claims.get("name", String.class),
                claims.get("email", String.class),
                claims.get("role", String.class)
        );
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public String extractName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}
