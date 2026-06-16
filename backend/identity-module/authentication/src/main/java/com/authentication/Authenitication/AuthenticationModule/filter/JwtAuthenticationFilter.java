package com.authentication.Authenitication.AuthenticationModule.filter;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.AuthenticationModule.security.JwtService;
import com.authentication.Authenitication.AuthenticationModule.service.UserService;
import com.authentication.Authenitication.role.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    /*Token Extraction
    Request arrives
      ↓
    Find JWT
      ↓
    Return JWT */
    private String extractToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            authenticateRequest(request);
        } catch (ExpiredJwtException | MalformedJwtException ex) {
            SecurityContextHolder.clearContext();
        }

        addRequestId(request, response);

        filterChain.doFilter(request, response);
    }

    //    Token Validation + User Identification
    private void authenticateRequest(HttpServletRequest request) {
        String token = extractToken(request);
        if (!isValidAuthenticationRequest(token)) {
            return;
        }
        String username = jwtService.extractUserName(token);
        AppUser user = userService.findByUsername(username);
        authenticateUser(token, user, request);
    }

    private boolean isValidAuthenticationRequest(String token) {

        return token != null
                && jwtService.isTokenValid(token)
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null;
    }

    private void authenticateUser(
            String token,
            AppUser user,
            HttpServletRequest request
    ) {

        Integer tokenVersion =
                jwtService.extractTokenVersion(token);

        if (!tokenVersion.equals(user.getTokenVersion())) {
            return;
        }

        Role activeRole =
                getActiveRole(user);

        if (activeRole == null) {
            return;
        }

        List<SimpleGrantedAuthority> authorities =
                buildAuthorities(activeRole);

        setAuthentication(user, authorities, request);
    }

    private List<SimpleGrantedAuthority> buildAuthorities(Role role) {

        return role.getPermissions()
                .stream()
                .map(permission ->
                        new SimpleGrantedAuthority(
                                permission.getName()))
                .toList();
    }

    //    Active Role
    private Role getActiveRole(AppUser user) {

        return user.getRoles()
                .stream()
                .filter(role ->
                        role.getName() == user.getActiveRole())
                .findFirst()
                .orElse(null);
    }

    //    SecurityContext Population
    private void setAuthentication(
            AppUser user,
            List<SimpleGrantedAuthority> authorities,
            HttpServletRequest request
    ) {

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );

        authToken.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );
        //SpringObject
        SecurityContextHolder.getContext()
                .setAuthentication(authToken);
    }

    // Request ID Logic
    private void addRequestId(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String requestId =
                request.getHeader("X-Request-Id");

        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        response.setHeader("X-Request-Id", requestId);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/auth/login") ||
                path.equals("/auth/register") ||
                path.equals("/auth/verify-otp") ||
                path.equals("/auth/resend-otp") ||
                path.equals("/auth/forgot-password") ||
                path.equals("/auth/reset-password");

    }


}
