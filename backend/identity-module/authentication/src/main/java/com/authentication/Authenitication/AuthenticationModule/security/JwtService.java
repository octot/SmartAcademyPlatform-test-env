package com.authentication.Authenitication.AuthenticationModule.security;


import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.Authorization.entity.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secret)
        );
    }


    // ⏱ Token valid for 15 minutes
    private static final long EXPIRATION_TIME = 900000;

    public String generateToken(CustomUserDetails userDetails, String activeRole) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        if (roles.isEmpty()) {
            throw new AppException("ROLE_003");
        }
        List<String> permissions = userDetails.getUser()
                .getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();
        if (permissions.isEmpty()) {
            throw new AppException("PERMISSION_002");
        }
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", userDetails.getId())
                //Used to invalid token on changes like password
                .claim("tokenVersion", userDetails.getTokenVersion())
                .claim("roles", roles)
                .claim("activeRole", activeRole)
                .claim("permissions", permissions)
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
        return !isTokenExpired(token) && isSignatureValid(token);
    }

    public Integer extractTokenVersion(String token) {
        return extractClaim(token,
                claims ->
                        claims.get("tokenVersion", Integer.class)
        );
    }

    public String extractActiveRole(String token) {
        return extractClaim(token, claims -> claims.get("activeRole", String.class));
    }

    public boolean isSignatureValid(String token) {
        try {
            Key key = getSigningKey();
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




