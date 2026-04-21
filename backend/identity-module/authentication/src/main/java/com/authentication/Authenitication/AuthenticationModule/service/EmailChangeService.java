package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.dto.EmailChangeRequestDto;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.entity.EmailChangeRequest;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.EmailChangeRepository;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.otp.OtpDeliveryService;
import com.authentication.Authenitication.AuthenticationModule.otp.OtpPurpose;
import com.authentication.Authenitication.AuthenticationModule.otp.OtpService;
import com.authentication.Authenitication.AuthenticationModule.otp.VerifyOtpRequestDTO;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class EmailChangeService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailChangeRepository emailChangeRepository;

    private final OtpDeliveryService otpDeliveryService;

    private final OtpService otpService;

    public EmailChangeService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailChangeRepository emailChangeRepository, OtpDeliveryService otpDeliveryService, OtpService otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailChangeRepository = emailChangeRepository;
        this.otpDeliveryService = otpDeliveryService;
        this.otpService = otpService;
    }

    @Transactional
    public void requestEmailChange(UUID userId, @Valid EmailChangeRequestDto dto) {
        // 1️⃣ Fetch user
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("USER_NOT_FOUND"));

        // 2️⃣ Check password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AppException("AUTH_004");
        }

        // 3️⃣ Prevent same email update
        if (user.getEmail().equalsIgnoreCase(dto.getNewEmail())) {
            throw new AppException("VAL_005");
        }

        // 4️⃣ Check if email already exists
        if (userRepository.existsByEmail(dto.getNewEmail())) {
            throw new AppException("VAL_006");
        }
        // 5️⃣ Delete any previous pending requests
        emailChangeRepository.deleteByUserId(userId);

        // 6️⃣ Create new email change request (5 min expiry)
        EmailChangeRequest request = new EmailChangeRequest();
        request.setUser(user);
        request.setNewEmail(dto.getNewEmail());
        request.setExpiryTime(Instant.now().plusSeconds(300));
        request.setVerified(false);

        emailChangeRepository.save(request);

        // 7️⃣ Generate OTP linked to this request
        String otp = otpService.generateOtp(user, OtpPurpose.EMAIL_CHANGE).getOtpValue();
        otpDeliveryService.sendOtp(
                dto.getNewEmail(),
                otp,
                OtpPurpose.EMAIL_CHANGE.getExpiryMinutes()
        );
    }

    @Transactional
    public void verifyEmailChange(UUID userId, VerifyOtpRequestDTO request){
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("AUTH_011"));

        EmailChangeRequest emailRequest = emailChangeRepository
                .findByUserIdAndVerifiedFalse(user.getId())
                .orElseThrow(() -> new AppException("EMAIL_001"));

        if (emailRequest.getExpiryTime().isBefore(Instant.now())) {
            throw new AppException("EMAIL_002");
        }

        otpService.verifyOtp(user, OtpPurpose.EMAIL_CHANGE, request.getOtp());

        user.setEmail(emailRequest.getNewEmail());
        userRepository.save(user);

        // 4️⃣ Delete request
        emailChangeRepository.delete(emailRequest);
    }

}
