package com.authentication.Authenitication.AuthenticationModule.config;


import com.authentication.Authenitication.AuthenticationModule.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> {})
                .csrf(
                        // ❌ Disable CSRF (JWT is stateless)
                        csrf -> csrf.disable())
                // ❌ Disable session creation
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ✅ Define public & secured endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/public/**",
                                "/auth/login",
                                "/auth/register",
                                "/auth/verify-otp",
                                "/error",
                                "/auth/resend-otp",
                                "/auth/forgot-password",
                                "/auth/reset-password",
                                "/dev/email",
                                "/actuator/**",
                                "/students/**"
                        ).permitAll()
                        .requestMatchers("/api/user/**").authenticated()
                        .anyRequest().denyAll()
                )
                // 🔐 Register JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
