package com.authentication.Authenitication.controller;


import com.authentication.Authenitication.dto.AboutUserResponse;
import com.authentication.Authenitication.dto.EmailChangeRequestDto;
import com.authentication.Authenitication.security.CustomUserDetails;
import com.authentication.Authenitication.service.EmailChangeService;
import com.authentication.Authenitication.verification.otp.VerifyOtpRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final EmailChangeService emailChangeService;


    public UserController(EmailChangeService emailChangeService) {
        this.emailChangeService = emailChangeService;
    }


    @GetMapping("/about")
    public ResponseEntity<?> aboutUser(Authentication auth) {
        CustomUserDetails user =
                (CustomUserDetails) auth.getPrincipal();
        AboutUserResponse response = new AboutUserResponse(
                user.getUsername(),
                user.getEmail(),
                auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
        return ResponseEntity.ok(response);

    }

    @PostMapping("/change/request")
    public ResponseEntity<?> changeEmail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody EmailChangeRequestDto dto

    ){
        Long userId = userDetails.getUser().getId();
        emailChangeService.requestEmailChange(userId, dto);
        return ResponseEntity.ok("OTP sent to new email");

    }

    @PostMapping("/verify/changeEmail")
    public ResponseEntity<?> verifyEmailChange(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid VerifyOtpRequestDTO requestDto

    ){
        Long userId = userDetails.getUser().getId();
        emailChangeService.verifyEmailChange(userId,requestDto);
        return ResponseEntity.ok("email success");

    }
}
