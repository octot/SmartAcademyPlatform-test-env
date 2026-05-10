package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.dto.AuthUserResponse;
import com.authentication.Authenitication.AuthenticationModule.dto.RegisterRequestDTO;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.entity.ResetPasswordRequest;
import com.authentication.Authenitication.AuthenticationModule.otp.*;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.service.PermissionService;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.util.UsernameValidator;
import com.authentication.Authenitication.user.entity.UserProfile;
import com.authentication.Authenitication.user.mapper.UserResponseBuilder;
import com.authentication.Authenitication.user.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final OtpDeliveryService otpDeliveryService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final UserResponseBuilder userResponseBuilder;
    public AuthService(UserRepository userRepository, OtpService otpService, OtpDeliveryService otpDeliveryService, PasswordEncoder passwordEncoder, RoleService roleService, TokenService tokenService, UserResponseBuilder userResponseBuilder) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.otpDeliveryService = otpDeliveryService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.userResponseBuilder = userResponseBuilder;
    }

    public VerifyOtpResponse verifyEmailOtp(VerifyOtpRequestDTO request) {

        AppUser user = userRepository.findByProfile_Email(request.getLogin())
                .orElseThrow(() -> new AppException("AUTH_011"));

        otpService.verifyOtp(user, request.getPurpose(), request.getOtp());

        switch (request.getPurpose()) {
            case SIGNUP:
                if (user.isEmailVerified()) {
                    throw new AppException("AUTH_007");
                }

                user.setEmailVerified(true);
                user.getProfile().setStatus(UserStatus.ACTIVE);
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
        AppUser user = userRepository.findByProfile_Email(email)
                .orElseThrow(() -> new AppException("AUTH_011"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    @Transactional
    public String resendEmailOtp(String email) {

        AppUser user = userRepository.findByProfile_Email(email)
                .orElseThrow(() -> new AppException("AUTH_011"));

        if (user.isEmailVerified()) {
            throw new AppException("AUTH_008");
        }
        otpService.otpResentLimitCheck(user, OtpPurpose.SIGNUP);
        Otp otp = otpService.generateOtp(user, OtpPurpose.SIGNUP);

        otpDeliveryService.sendOtp(
                user.getProfile().getEmail(),
                otp.getOtpValue(),
                OtpPurpose.SIGNUP.getExpiryMinutes()
        );
        return otp.getOtpValue();
    }

    @Transactional
    public String forgotEmailOtp(String email) {

        AppUser user = userRepository.findByProfile_Email(email)
                .orElseThrow(() -> new AppException("AUTH_011"));


        otpService.otpResentLimitCheck(user, OtpPurpose.PASSWORD_RESET);
        Otp otp = otpService.generateOtp(user, OtpPurpose.PASSWORD_RESET);

        otpDeliveryService.sendOtp(
                user.getProfile().getEmail(),
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


}