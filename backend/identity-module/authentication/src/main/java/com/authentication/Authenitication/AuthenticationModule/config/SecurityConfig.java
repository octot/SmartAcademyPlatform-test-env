package com.authentication.Authenitication.AuthenticationModule.config;


import com.authentication.Authenitication.AuthenticationModule.filter.JwtAuthenticationFilter;
import com.authentication.Authenitication.AuthenticationModule.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableMethodSecurity  //Helps to check on methods like PreAuthorize without this anyone can no auth  access
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> {
                })
                .csrf(
                        // ❌ Disable CSRF (JWT is stateless)
                        AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 🔥 THIS LINE
                )
                // ❌ Disable session creation
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ✅ Define public & secured endpoints
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC AUTH ENDPOINTS
                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/auth/verify-otp",
                                "/auth/resend-otp",
                                "/auth/forgot-password",
                                "/auth/reset-password",
                                "/auth/logout"
                        ).permitAll()

                        // PROTECTED AUTH ENDPOINTS
                        .requestMatchers("/auth/**").authenticated()

                        // OTHER APIs
                        .requestMatchers("/api/**").authenticated()

                        .requestMatchers("/test/**").authenticated()

                        // FALLBACK
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
