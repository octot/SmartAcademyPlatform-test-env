package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.dto.RegisterRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.entity.ResetPasswordRequest;
import com.authentication.Authenitication.AuthenticationModule.otp.*;
import com.authentication.Authenitication.Authorization.entity.Role;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.util.UsernameValidator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final OtpDeliveryService otpDeliveryService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, OtpService otpService, OtpDeliveryService otpDeliveryService, PasswordEncoder passwordEncoder, RoleService roleService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.otpDeliveryService = otpDeliveryService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.tokenService = tokenService;
    }

    public VerifyOtpResponse verifyEmailOtp(VerifyOtpRequestDTO request) {

        AppUser user = userRepository.findByEmail(request.getLogin())
                .orElseThrow(() -> new AppException("AUTH_011"));

        otpService.verifyOtp(user, request.getPurpose(), request.getOtp());

        switch (request.getPurpose()) {
            case SIGNUP:
                if (user.isEmailVerified()) {
                    throw new AppException("AUTH_007");
                }

                user.setEmailVerified(true);
                user.setStatus(UserStatus.ACTIVE);
                userRepository.save(user);
                return new VerifyOtpResponse("Signup verified successfully");

            case PASSWORD_RESET:
                String resetToken = tokenService.generateResetToken(user);
                return new VerifyOtpResponse("OTP verified", resetToken);

            default:
                throw new AppException("AUTH_017");
        }

    }

    public void resetPassword(ResetPasswordRequest request) {
        String email = tokenService.validateResetToken(request.getResetToken());
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("AUTH_011"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    @Transactional
    public String resendEmailOtp(String email) {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("AUTH_011"));

        if (user.isEmailVerified()) {
            throw new AppException("AUTH_008");
        }
        otpService.otpResentLimitCheck(user, OtpPurpose.SIGNUP);
        Otp otp = otpService.generateOtp(user, OtpPurpose.SIGNUP);

        otpDeliveryService.sendOtp(
                user.getEmail(),
                otp.getOtpValue(),
                OtpPurpose.SIGNUP.getExpiryMinutes()
        );
        return otp.getOtpValue();
    }

    @Transactional
    public String forgotEmailOtp(String email) {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("AUTH_011"));


        otpService.otpResentLimitCheck(user, OtpPurpose.PASSWORD_RESET);
        Otp otp = otpService.generateOtp(user, OtpPurpose.PASSWORD_RESET);

        otpDeliveryService.sendOtp(
                user.getEmail(),
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
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException("AUTH_007");
        }
    }

    public void createUser(RegisterRequestDTO request, Set<Role> userRole) {
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(userRole);
        user.setEmailVerified(false);
        user.setStatus(UserStatus.PENDING);

        String otpValue = OtpUtil.generateOtp();
        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtpValue(otpValue);
        otp.setPurpose(OtpPurpose.SIGNUP);
        otp.setExpiryTime(Instant.now().plus(5, ChronoUnit.MINUTES));
        otp.setAttemptCount(0);
        otp.setMaxAttempts(3);
        otp.setUsed(false);
        userRepository.save(user);
    }

    public void register(RegisterRequestDTO request) {
        checkUserNameAndEmailExist(request);
        createUser(request, Set.of(roleService.getDefaultUserRole()));
    }

    public void registerForAdmin(RegisterRequestDTO request) {
        checkUserNameAndEmailExist(request);
        createUser(request, Set.of(roleService.getAdminUserRole()));
    }

}