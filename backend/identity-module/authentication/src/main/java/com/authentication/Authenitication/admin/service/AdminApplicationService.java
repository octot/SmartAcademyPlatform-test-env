package com.authentication.Authenitication.admin.service;

import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.otp.OtpPurpose;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.service.AuthService;
import com.authentication.Authenitication.admin.dto.AdminApplicationRequestDTO;
import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import com.authentication.Authenitication.admin.repository.AdminRegistrationRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminApplicationService {

    private final AdminRegistrationRequestRepository adminRequestRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    public void applyForAdmin(
            AdminApplicationRequestDTO request
    ) {

        validateUsername(request.getUsername());

        validateEmail(request.getEmail());

        AdminRegistrationRequest adminRequest =
                buildAdminRequest(request);

        adminRequestRepository.save(adminRequest);

        authService.sendOtp(request.getEmail(), OtpPurpose.ADMIN_EMAIL_VERIFICATION);
    }

    private void validateUsername(String username) {

        if (userRepository.existsByUsername(username)) {
            throw new AppException(
                    "AUTH_006",
                    "username"
            );
        }

        if (adminRequestRepository.existsByUsername(username)) {
            throw new AppException(
                    "ADMIN_REQ_001",
                    "username"
            );
        }
    }

    private void validateEmail(String email) {

        if (userRepository.existsByProfile_Email(email)) {
            throw new AppException(
                    "AUTH_007",
                    "email"
            );
        }

        if (adminRequestRepository.existsByEmail(email)) {
            throw new AppException(
                    "ADMIN_REQ_002",
                    "email"
            );
        }
    }

    private AdminRegistrationRequest buildAdminRequest(AdminApplicationRequestDTO request) {
        return AdminRegistrationRequest.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .purpose(request.getPurpose())
                .description(request.getDescription())
                .status(ApprovalStatus.PENDING)
                .build();
    }


}
