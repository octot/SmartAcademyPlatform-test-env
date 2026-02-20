package com.authentication.Authenitication.controller;

import com.authentication.Authenitication.dto.ChangePasswordRequest;
import com.authentication.Authenitication.dto.LoginRequest;
import com.authentication.Authenitication.dto.RegisterRequestDTO;
import com.authentication.Authenitication.security.CustomUserDetails;
import com.authentication.Authenitication.verification.otp.ResendOtpRequestDTO;
import com.authentication.Authenitication.security.JwtUtil;
import com.authentication.Authenitication.service.CustomUserDetailsService;
import com.authentication.Authenitication.verification.otp.OtpService;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final OtpService otpService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtService, CustomUserDetailsService customUserDetailsService, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.otpService = otpService;
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
        customUserDetailsService.existsByUsername(request.getUsername());
        customUserDetailsService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                            Authentication authentication) {
        customUserDetailsService.changePassword(request, authentication.getName());
        return ResponseEntity.ok("Password changed successfully");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequestDTO request) {
        customUserDetailsService.existsByUsername(request.getUsername());
        customUserDetailsService.registerForAdmin(request);
        return ResponseEntity.ok("User registered successFully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequestDTO request) {
        otpService.verifyEmailOtp(request);
        return ResponseEntity.ok(
                Map.of("message", "Email verified successfully. You can now login.")
        );
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequestDTO request) {
        String otp = otpService.resendEmailOtp(request.getEmail());
        return ResponseEntity.ok(Map.of("OtpSuccess", otp));
    }

}
