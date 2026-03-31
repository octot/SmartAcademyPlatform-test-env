package com.authentication.Authenitication.AuthenticationModule.controller;

import com.authentication.Authenitication.AuthenticationModule.dto.*;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.AuthenticationModule.security.JwtUtil;
import com.authentication.Authenitication.AuthenticationModule.service.AuthService;
import com.authentication.Authenitication.AuthenticationModule.service.PasswordService;
import com.authentication.Authenitication.AuthenticationModule.service.SecurityUserDetailsService;
import com.authentication.Authenitication.AuthenticationModule.service.UserService;
import com.authentication.Authenitication.AuthenticationModule.otp.ResendOtpRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.otp.VerifyOtpRequestDTO;
import com.authentication.Authenitication.Authorization.entity.Permission;
import com.authentication.Authenitication.Authorization.entity.Role;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public LoginResponse login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        CustomUserDetails user =
                (CustomUserDetails) customUserDetailsService
                        .loadUserByUsername(request.getLogin());
        String token = jwtService.generateToken(user);

        AppUser userDetails = userService.findByUsername(request.getLogin());

        UserDto userDto = new UserDto(userDetails.getId(), userDetails.getUsername());
        List<String> roles = userDetails.getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        List<String> permissions = userDetails.getRoles().stream()
                .flatMap(role -> role.getPermission().stream())
                .map(Permission::getName)
                .distinct()
                .toList();

        return new LoginResponse(token, userDto, permissions, roles);
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
