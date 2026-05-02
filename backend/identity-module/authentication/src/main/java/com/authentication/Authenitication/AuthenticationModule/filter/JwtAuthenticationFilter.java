package com.authentication.Authenitication.AuthenticationModule.filter;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.AuthenticationModule.security.JwtService;
import com.authentication.Authenitication.AuthenticationModule.service.SecurityUserDetailsService;
import com.authentication.Authenitication.AuthenticationModule.service.UserService;
import com.authentication.Authenitication.role.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SecurityUserDetailsService userDetailsService;
    private final UserService userService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            SecurityUserDetailsService userDetailsService, UserService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = extractToken(request);

            if (token != null && jwtService.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtService.extractUserName(token);
                AppUser user = userService.findByUsername(username);
                CustomUserDetails userDetails =
                        new CustomUserDetails(user);

                Integer tokenVersion = jwtService.extractTokenVersion(token);

                Optional<Role> roleOpt = user.getRoles()
                        .stream()
                        .filter(role -> role.getName() == user.getActiveRole())
                        .findFirst();

                if (roleOpt.isEmpty()) {
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(request, response);
                    return;
                }
                Role activeRoleEntity = roleOpt.get();

//               Authorities = ["USER_VIEW", "USER_APPROVE"]
                List<SimpleGrantedAuthority> authorities =
                        activeRoleEntity.getPermissions()
                                .stream()
                                .map(p -> new SimpleGrantedAuthority(p.getName()))
                                .toList();

                // 4️⃣ Application-level check
                if (tokenVersion.equals(user.getTokenVersion())) {
                    UsernamePasswordAuthenticationToken
                            authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }

            }
        } catch (ExpiredJwtException | MalformedJwtException ex) {
            SecurityContextHolder.clearContext();
        }
        //Creation logic for request id
        String requestId = request.getHeader("X-Request-Id");
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }
        response.setHeader("X-Request-Id", requestId);
        filterChain.doFilter(request, response);
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


    private String extractToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
