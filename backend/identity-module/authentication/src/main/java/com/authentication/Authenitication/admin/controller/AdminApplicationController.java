package com.authentication.Authenitication.admin.controller;


import com.authentication.Authenitication.AuthenticationModule.otp.VerifyOtpRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.service.AuthService;
import com.authentication.Authenitication.admin.dto.AdminApplicationRequestDTO;
import com.authentication.Authenitication.admin.service.AdminApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AdminApplicationController {
    private final AdminApplicationService
            adminApplicationService;
    private final AuthService authService;

    @PostMapping("/admin-request")
    public ResponseEntity<String> applyForAdmin(
            @RequestBody
            AdminApplicationRequestDTO request
    ) {

        adminApplicationService
                .applyForAdmin(request);

        return ResponseEntity.ok(
                "Admin application submitted successfully"
        );
    }

    @PostMapping("/verify-admin-otp")
    public void verifyAdminOtp(
            @RequestBody VerifyOtpRequestDTO request) {
        authService.verifyAdminEmailOtp(request);
    }

}
