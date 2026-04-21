package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    private final SecretKey key;

    public TokenService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateResetToken(AppUser user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("purpose", "PASSWORD_RESET")
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .signWith(key) // ✅ new method
                .compact();
    }

    public String validateResetToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // or verifyWith if available
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


}
