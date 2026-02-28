package com.authentication.Authenitication.controller;

import com.authentication.Authenitication.dto.ChangePasswordRequest;
import com.authentication.Authenitication.dto.LoginRequest;
import com.authentication.Authenitication.dto.RegisterRequestDTO;
import com.authentication.Authenitication.security.CustomUserDetails;
import com.authentication.Authenitication.security.JwtUtil;
import com.authentication.Authenitication.service.AuthService;
import com.authentication.Authenitication.service.PasswordService;
import com.authentication.Authenitication.service.SecurityUserDetailsService;
import com.authentication.Authenitication.service.UserService;
import com.authentication.Authenitication.verification.otp.ResendOtpRequestDTO;
import com.authentication.Authenitication.verification.otp.VerifyOtpRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtService;
    private final SecurityUserDetailsService customUserDetailsService;
    private final AuthService authService;
    private final UserService userService;
    private final PasswordService passwordService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtService, SecurityUserDetailsService customUserDetailsService, AuthService authService, UserService userService, PasswordService passwordService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.authService = authService;
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        CustomUserDetails user =
                (CustomUserDetails) customUserDetailsService
                        .loadUserByUsername(request.getLogin());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        userService.existsByUsername(request.getUsername());
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                            Authentication authentication) {
        passwordService.changePassword(request, authentication.getName());
        return ResponseEntity.ok("Password changed successfully");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequestDTO request) {
        userService.existsByUsername(request.getUsername());
        authService.registerForAdmin(request);
        return ResponseEntity.ok("User registered successFully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequestDTO request) {
        authService.verifyEmailOtp(request);
        return ResponseEntity.ok(
                Map.of("message", "Email verified successfully. You can now login.")
        );
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequestDTO request) {
        String otp = authService.resendEmailOtp(request.getEmail());
        return ResponseEntity.ok(Map.of("OtpSuccess", otp));
    }

}
