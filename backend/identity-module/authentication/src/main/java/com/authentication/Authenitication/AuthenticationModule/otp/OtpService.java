package com.authentication.Authenitication.AuthenticationModule.otp;

import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.otp.enums.VerificationChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
@Transactional
public class OtpService {
    private final OtpRepository otpRepository;

    public OtpService(
            OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public Otp generateOtp(String target,
                           VerificationChannel channel, OtpPurpose purpose) {

        otpRepository.invalidateActiveOtp(target, purpose);

        String otpValue = OtpUtil.generateOtp();

        Otp otp = new Otp();
        otp.setTarget(target);
        otp.setChannel(channel);
        otp.setPurpose(purpose);
        otp.setOtpValue(otpValue);
        otp.setCreatedAt(Instant.now());
        otp.setExpiryTime(
                Instant.now().plus(
                        purpose.getExpiryMinutes(), ChronoUnit.MINUTES)
        );
        otp.setAttemptCount(0);
        otp.setMaxAttempts(purpose.getMaxAttempts());
        otp.setUsed(false);

        return otpRepository.save(otp);
    }

    @Transactional(noRollbackFor = AppException.class)
    public void verifyOtp(String target, OtpPurpose purpose, String inputOtp) {

        Otp otp = otpRepository.findActiveOtp(target, purpose)
                .orElseThrow(() -> new AppException("AUTH_013"));

        if (otp.getExpiryTime().isBefore(Instant.now())) {
            throw new AppException("AUTH_010");
        }

        if (otp.getAttemptCount() >= otp.getMaxAttempts()) {
            throw new AppException("AUTH_014");
        }

        otp.setAttemptCount(otp.getAttemptCount() + 1);

        if (!otp.getOtpValue().equals(inputOtp)) {
            otpRepository.save(otp);
            throw new AppException("AUTH_012"); // Invalid OTP
        }

        otp.setUsed(true);
        otpRepository.save(otp);
    }


    @Transactional
    public void otpResentLimitCheck(String target, OtpPurpose purpose) {
        Instant now = Instant.now();

        Otp latestOtp = otpRepository
                .findTopByTargetAndPurposeOrderByCreatedAtDesc(target, purpose).
                orElse(null);

        if (latestOtp != null &&
                latestOtp.getCreatedAt().isAfter(now.minusSeconds(60))) {
            throw new AppException("AUTH_015");
        }

        long last10MinCount = otpRepository
                .countRecentOtps(
                        target,
                        purpose,
                        now.minus(10, ChronoUnit.MINUTES)
                );

        if (last10MinCount >= 3) {
            throw new AppException("AUTH_016");
        }

        if (latestOtp != null && !latestOtp.isUsed()) {
            latestOtp.setUsed(true);
            otpRepository.save(latestOtp);
        }

    }


}
