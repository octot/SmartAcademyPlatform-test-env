package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.dto.AuthUserResponse;
import com.authentication.Authenitication.AuthenticationModule.dto.RegisterRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.entity.ResetPasswordRequest;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.otp.*;
import com.authentication.Authenitication.AuthenticationModule.otp.enums.VerificationChannel;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.AuthenticationModule.util.UsernameValidator;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.repository.AdminRegistrationRequestRepository;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.user.entity.UserProfile;
import com.authentication.Authenitication.user.mapper.UserResponseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final OtpDeliveryService otpDeliveryService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final UserResponseBuilder userResponseBuilder;
    private final AdminRegistrationRequestRepository adminRequestRepository;

    public AuthService(UserRepository userRepository, OtpService otpService, OtpDeliveryService otpDeliveryService, PasswordEncoder passwordEncoder, RoleService roleService, TokenService tokenService, UserResponseBuilder userResponseBuilder, AdminRegistrationRequestRepository adminRequestRepository) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.otpDeliveryService = otpDeliveryService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.userResponseBuilder = userResponseBuilder;
        this.adminRequestRepository = adminRequestRepository;
    }

    public VerifyOtpResponse verifyOtp(
            VerifyOtpRequestDTO request
    ) {

        switch (request.getPurpose()) {

            case SIGNUP -> {
                AppUser user = userRepository
                        .findByProfile_Email(
                                request.getLogin()
                        )
                        .orElseThrow(
                                () -> new AppException("AUTH_011")
                        );
                otpService.verifyOtp(
                        request.getLogin(),
                        request.getPurpose(),
                        request.getOtp()
                );

                if (user.isEmailVerified()) {
                    throw new AppException("AUTH_007");
                }

                user.setEmailVerified(true);

                user.getProfile()
                        .setStatus(UserStatus.ACTIVE);

                userRepository.save(user);

                return new VerifyOtpResponse(
                        "Signup verified successfully"
                );
            }

            case PASSWORD_RESET -> {

                AppUser user = userRepository
                        .findByProfile_Email(
                                request.getLogin()
                        )
                        .orElseThrow(
                                () -> new AppException("AUTH_011")
                        );

                otpService.verifyOtp(
                        request.getLogin(),
                        request.getPurpose(),
                        request.getOtp()
                );

                String resetToken =
                        tokenService.generateResetToken(user);

                return new VerifyOtpResponse(
                        "OTP verified",
                        resetToken
                );
            }

            case ADMIN_EMAIL_VERIFICATION -> {

                AdminRegistrationRequest adminRequest =
                        adminRequestRepository
                                .findByEmail(
                                        request.getLogin()
                                )
                                .orElseThrow(
                                        () -> new AppException(
                                                "ADMIN_REQ_003"
                                        )
                                );

                otpService.verifyOtp(
                        request.getLogin(),
                        request.getPurpose(),
                        request.getOtp()
                );

                adminRequest.setEmailVerified(true);

                adminRequestRepository.save(adminRequest);

                return new VerifyOtpResponse(
                        "Admin email verified successfully"
                );
            }

            default -> throw new AppException("AUTH_017");
        }
    }

    public void verifyAdminEmailOtp(
            VerifyOtpRequestDTO request
    ) {

        AdminRegistrationRequest adminRequest =
                adminRequestRepository
                        .findByEmail(request.getLogin())
                        .orElseThrow(
                                () -> new AppException(
                                        "ADMIN_REQ_003"
                                )
                        );

        otpService.verifyOtp(
                adminRequest.getEmail(),
                request.getPurpose(),
                request.getOtp()
        );

        adminRequest.setEmailVerified(true);

        adminRequestRepository.save(adminRequest);
    }

    public void resetPassword(ResetPasswordRequest request) {
        String email = tokenService.validateResetToken(request.getResetToken());
        AppUser user = userRepository.findByProfile_Email(email)
                .orElseThrow(() -> new AppException("AUTH_011"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    @Transactional
    public String sendOtp(String target, OtpPurpose purpose) {

        validateOtpResendTarget(
                target,
                purpose
        );
        //IMP
        otpService.otpResentLimitCheck(target, purpose);

        Otp otp = otpService.generateOtp(target, VerificationChannel.EMAIL, purpose);

        otpDeliveryService.sendOtp(
                target,
                otp.getOtpValue(),
                purpose.getExpiryMinutes()
        );
        return otp.getOtpValue();
    }

    @Transactional
    public String forgotOtp(String target) {

        otpService.otpResentLimitCheck(target, OtpPurpose.PASSWORD_RESET);
        Otp otp = otpService.generateOtp(target, VerificationChannel.EMAIL, OtpPurpose.PASSWORD_RESET);

        otpDeliveryService.sendOtp(
                target,
                otp.getOtpValue(),
                OtpPurpose.PASSWORD_RESET.getExpiryMinutes()
        );
        return otp.getOtpValue();
    }

    public void checkUserNameAndEmailExist(RegisterRequestDTO request) {
        UsernameValidator.validate(request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException("AUTH_006");
        }
        if (userRepository.existsByProfile_Email(request.getEmail())) {
            throw new AppException("AUTH_007");
        }
    }

    public void createUser(RegisterRequestDTO request) {
        AppUser user = new AppUser();
        Set<Role> userRole = Set.of(roleService.getUserRole(request.getRole()));
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(userRole);
        user.setEmailVerified(false);
        user.setActiveRole(request.getRole());
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setEmail(request.getEmail());
        userProfile.setStatus(UserStatus.PENDING);
        user.setProfile(userProfile);
        userRepository.save(user);
    }

    public void register(RegisterRequestDTO request) {
        checkUserNameAndEmailExist(request);
        createUser(request);
    }

    //TODO need to handle role for admin
    public void registerForAdmin(RegisterRequestDTO request) {
        checkUserNameAndEmailExist(request);
        createUser(request);
    }

    public AuthUserResponse switchRole(CustomUserDetails userDetails, String requestedRole) {

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        // 🔥 VALIDATION
        if (!roles.contains(requestedRole)) {
            throw new AppException("ROLE_004");
        }
        AppUser user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new AppException("USER_NOT_FOUND"));

        // 🔥 Step 3: update DB (IMPORTANT)
        user.setActiveRole(RoleName.valueOf(requestedRole));
        userRepository.save(user);

        return userResponseBuilder.buildUserResponse(user);

    }

    private void validateOtpResendTarget(
            String target,
            OtpPurpose purpose
    ) {

        switch (purpose) {

            case SIGNUP -> {

                AppUser user = userRepository
                        .findByProfile_Email(target)
                        .orElseThrow(
                                () -> new AppException("AUTH_011")
                        );

                if (user.isEmailVerified()) {
                    throw new AppException("AUTH_008");
                }
            }

            case ADMIN_EMAIL_VERIFICATION -> {

                AdminRegistrationRequest adminRequest =
                        adminRequestRepository
                                .findByEmail(target)
                                .orElseThrow(
                                        () -> new AppException(
                                                "ADMIN_REQ_003"
                                        )
                                );

                if (adminRequest.isEmailVerified()) {
                    throw new AppException(
                            "ADMIN_REQ_004"
                    );
                }
            }

            case PASSWORD_RESET -> {

                userRepository.findByProfile_Email(target)
                        .orElseThrow(
                                () -> new AppException("AUTH_011")
                        );
            }

            default -> throw new AppException("AUTH_017");
        }
    }

}