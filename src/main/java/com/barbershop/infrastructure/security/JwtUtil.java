package com.barbershop.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
/**
 * Utility class responsible for generating, validating, and extracting data
 * from JWT tokens. Uses a secure HMAC key and supports claim extraction,
 * expiration checks, and username retrieval from the token payload.
 */

@Component
public class JwtUtil {

    private static final String SECRET = "troque-por-um-segredo-super-seguro-de-no-minimo-32-bytes!!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long EXP_MS = 1000L * 60 * 60 * 24; // 24h

    // -------------------------------
    //         GERAR TOKEN
    // -------------------------------
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    // -------------------------------
    //     EXTRAIR USERNAME
    // -------------------------------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // -------------------------------
    //       EXTRAIR UMA CLAIM
    // -------------------------------
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    // -------------------------------
    //         VALIDAR TOKEN
    // -------------------------------
    public boolean isTokenValid(String token, String username) {
        try {
            final String subject = extractUsername(token);
            return (subject.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
