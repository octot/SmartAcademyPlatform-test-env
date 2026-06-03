package com.authentication.Authenitication.AuthenticationModule.controller;

import com.authentication.Authenitication.AuthenticationModule.dto.*;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.entity.ResetPasswordRequest;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.otp.ForgotRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.otp.ResendOtpRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.otp.VerifyOtpRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.otp.VerifyOtpResponse;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.AuthenticationModule.security.JwtService;
import com.authentication.Authenitication.AuthenticationModule.service.AuthService;
import com.authentication.Authenitication.AuthenticationModule.service.PasswordService;
import com.authentication.Authenitication.AuthenticationModule.service.SecurityUserDetailsService;
import com.authentication.Authenitication.AuthenticationModule.service.UserService;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.entity.Permission;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import com.authentication.Authenitication.admin.repository.AdminRegistrationRequestRepository;
import com.authentication.Authenitication.role.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityUserDetailsService customUserDetailsService;
    private final AuthService authService;
    private final UserService userService;
    private final PasswordService passwordService;
    private final AdminRegistrationRequestRepository adminRequestRepository;


    //    NEED TO CHECK THE ADMIN NOT ABLE TO LOGIN AFTER CREATION
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<AppUser> existingUser =
                userService.findByProfile_Email(
                        request.getLogin()
                );
        if (existingUser.isEmpty()) {

            handlePendingAdminRequest(
                    request.getLogin()
            );

            throw new AppException("AUTH_011");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        CustomUserDetails user =
                customUserDetailsService
                        .loadUserByUsername(request.getLogin());
        List<String> currentRoles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        if (currentRoles.isEmpty()) {
            throw new AppException("ROLE_003");
        }

        String activeRole = user.getUser().getActiveRole().name();
        String token = jwtService.generateToken(user);

        AppUser userDetails = userService.findByUsername(request.getLogin());
        UserDto userDto = new UserDto(userDetails.getId(), userDetails.getUsername());
        List<RoleName> roles = userDetails.getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        List<String> permissions = userDetails.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();

        ResponseCookie responseCookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60)
                .sameSite("Lax")
                .build();
        LoginResponse response = new LoginResponse(userDto, permissions, roles, activeRole);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);

    }

    private void handlePendingAdminRequest(
            String email
    ) {

        adminRequestRepository
                .findByEmail(email)
                .ifPresent(request -> {

                    if (request.getStatus()
                            == ApprovalStatus.PENDING) {

                        throw new AppException(
                                "ADMIN_REQ_008"
                        );
                    }

                    if (request.getStatus()
                            == ApprovalStatus.REJECTED) {

                        throw new AppException(
                                "ADMIN_REQ_009"
                        );
                    }
                });
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

    @PreAuthorize("hasAuthority('ADMIN_CREATE_GLOBAL')")
    @PostMapping("/create-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequestDTO request) {
        userService.existsByUsername(request.getUsername());
        authService.registerForAdmin(request);
        return ResponseEntity.ok("User registered successFully");
    }

    @PostMapping("/verify-otp")
    public VerifyOtpResponse verifyOtp(
            @RequestBody VerifyOtpRequestDTO request) {
        return authService.verifyOtp(request);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequestDTO request) {
        authService.sendOtp(request.getLogin(), request.getPurpose());
        return ResponseEntity.ok("OtpSuccess");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotRequestDTO request) {
        String otp = authService.forgotOtp(request.getLogin());
        return ResponseEntity.ok(Map.of("OtpSuccess", otp));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // use true in production (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(0); // 🔥 delete cookie

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

}
