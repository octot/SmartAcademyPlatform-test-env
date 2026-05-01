package com.authentication.Authenitication.AuthenticationModule.controller;


import com.authentication.Authenitication.AuthenticationModule.dto.*;
import com.authentication.Authenitication.AuthenticationModule.entity.UserListResponse;
import com.authentication.Authenitication.AuthenticationModule.otp.VerifyOtpRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.AuthenticationModule.service.AuthService;
import com.authentication.Authenitication.AuthenticationModule.service.EmailChangeService;
import com.authentication.Authenitication.AuthenticationModule.service.UserService;
import com.authentication.Authenitication.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final EmailChangeService emailChangeService;
    private final AuthService authService;
    private final UserService userService;


    public UserController(EmailChangeService emailChangeService, AuthService authService, UserService userService) {
        this.emailChangeService = emailChangeService;
        this.authService = authService;
        this.userService = userService;
    }


    //TODO currently roles and permissions fetched from db but should be fetched from CustomerUserDetails
    @GetMapping("/me")
    public ResponseEntity<?> aboutUser(Authentication auth) {
        // ✅ Safety check
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = auth.getName();
        AuthUserResponse user = userService.getUserWithRolesAndPermissions(username);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/change/request")
    public ResponseEntity<?> changeEmail(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,
            @Valid @RequestBody EmailChangeRequestDto dto

    ) {
        UUID userId = userDetails.getUser().getId();
        emailChangeService.requestEmailChange(userId, dto);
        return ResponseEntity.ok("OTP sent to new email");

    }

    @PostMapping("/verify/changeEmail")
    public ResponseEntity<?> verifyEmailChange(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid VerifyOtpRequestDTO requestDto

    ) {
        UUID userId = userDetails.getUser().getId();
        emailChangeService.verifyEmailChange(userId, requestDto);
        return ResponseEntity.ok("email success");

    }

    @PostMapping("/switch-role")
    public ResponseEntity<AuthUserResponse> switchRole(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody SwitchRoleRequest request
    ) {
        AuthUserResponse response = authService.switchRole(userDetails, request.getRole());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAnyAuthority('USER_EDIT_OWN','USER_EDIT_DEPARTMENT','USER_EDIT_GLOBAL')")
    public List<UserListResponse> getUsers(Authentication authentication) {
        return userService.getUsers(authentication);
    }

    @PatchMapping("/updateUser/{username}")
    @PreAuthorize("hasAnyAuthority('USER_EDIT_OWN','USER_EDIT_DEPARTMENT','USER_EDIT_GLOBAL')")
    public UserResponse updateUser(@PathVariable String username,
                                   @RequestBody UpdateUserRequest dto,
                                   Authentication auth) {
        return userService.updateUser(username, dto, auth);
    }

    @PostMapping("/add-role")
    public ResponseEntity<AuthUserResponse> addRole(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddRoleRequest request) {

        AuthUserResponse response =
                userService.addRole(userDetails, request.getRole());

        return ResponseEntity.ok(response);
    }
}
