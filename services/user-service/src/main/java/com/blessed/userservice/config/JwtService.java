package com.blessed.userservice.config;

import com.blessed.userservice.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private static String SECRET_KEY;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 час

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Генерация токена с кастомными claims
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("role", user.getRole().toString());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail()) // subject = email
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey())
                .compact();
    }

    // Достать username (email) из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Достать userId
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    // Достать роль
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Достать имя
    public String extractName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    // Достать email
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    // Проверка валидности токена
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
