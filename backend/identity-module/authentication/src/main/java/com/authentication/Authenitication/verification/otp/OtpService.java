package com.authentication.Authenitication.verification.otp;


import com.authentication.Authenitication.entity.AppUser;
import com.authentication.Authenitication.enums.UserStatus;
import com.authentication.Authenitication.exception.AppException;
import com.authentication.Authenitication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class OtpService {
    private final UserRepository userRepository;
    private final OtpDeliveryService otpDeliveryService;
    private final OtpRepository otpRepository;

    public OtpService(UserRepository userRepository,
                      OtpDeliveryService otpDeliveryService, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.otpDeliveryService = otpDeliveryService;
        this.otpRepository = otpRepository;
    }


    public void verifyEmailOtp(VerifyOtpRequestDTO request) {

        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new AppException("AUTH_011"));
        // 1. Already verified?
        if (user.isEmailVerified()) {
            throw new AppException("AUTH_007");
        }

        Otp otp = otpRepository.findActiveOtp(user.getId(), OtpPurpose.SIGNUP)
                .orElseThrow(() -> new AppException("AUTH_013"));

        // 3️⃣ Check expiry
        if (otp.getExpiryTime().isBefore(Instant.now())) {
            throw new AppException("AUTH_010"); // OTP expired
        }
        // 4️⃣ Check attempt limit
        if (otp.getAttemptCount() >= otp.getMaxAttempts()) {
            throw new AppException("AUTH_013"); // Max attempts exceeded
        }

        otp.setUsed(true);
        otpRepository.save(otp);
        // 4. SUCCESS → activate user
        user.setEmailVerified(true);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

    }

    @Transactional
    public String resendEmailOtp(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new AppException("AUTH_011"));

        if (user.isEmailVerified()) {
            throw new AppException("AUTH_008");
        }
        // 2️⃣ Delete previous unused SIGNUP OTP
        otpRepository.deleteByUser_IdAndPurpose(user.getId(), OtpPurpose.SIGNUP);
        String newOtp = OtpUtil.generateOtp();

        Otp otp = new Otp();
        otp.setUser(user);
        otp.setPurpose(OtpPurpose.SIGNUP);
        otp.setOtpValue(newOtp);
        otp.setCreatedAt(Instant.now());
        otp.setExpiryTime(
                Instant.now().plus(OtpPurpose.SIGNUP.getExpiryMinutes(), ChronoUnit.MINUTES)
        );
        otp.setAttemptCount(0);
        otp.setMaxAttempts(OtpPurpose.SIGNUP.getMaxAttempts());
        otp.setUsed(false);
        otpRepository.save(otp);
        // 4️⃣ Send OTP
        otpDeliveryService.sendOtp(
                user.getEmail(),
                newOtp,
                OtpPurpose.SIGNUP.getExpiryMinutes()
        );

        return newOtp;
    }

}
