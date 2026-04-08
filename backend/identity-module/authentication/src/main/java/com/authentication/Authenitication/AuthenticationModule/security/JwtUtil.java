package com.authentication.Authenitication.AuthenticationModule.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        System.out.println("FromJWTUtil "+ secret);
        return Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
    }
    // ⏱ Token valid for 15 minutes
    private static final long EXPIRATION_TIME = 900000;

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId",userDetails.getId())
                //Used to invalid token on changes like password
                .claim("tokenVersion", userDetails.getTokenVersion())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(getSigningKey())
                .compact();
    }

    // Read username from token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Validate token
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token) && isSignatureValid(token);}

    public Integer extractTokenVersion(String token) {
        return extractClaim(token,
                claims ->
                claims.get("tokenVersion", Integer.class)
        );
    }

    public boolean isSignatureValid(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 🔥 THIS validates signature
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }


}
