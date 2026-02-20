package com.authentication.Authenitication.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key";
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // ⏱ Token valid for 15 minutes
    private static final long EXPIRATION_TIME = 15 * 60 * 1000;

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                //Used to invalid token on changes like password
                .claim("tokenVersion", userDetails.getTokenVersion())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(SECRET_KEY)
                .compact();
    }

    // Read username from token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUserName(token).equals
                (userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public Integer extractTokenVersion(String token) {
        return extractClaim(token, claims ->
                claims.get("tokenVersion", Integer.class)
        );
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }


}
