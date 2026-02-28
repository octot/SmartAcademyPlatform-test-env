package com.authentication.Authenitication.verification.otp;

import com.authentication.Authenitication.entity.AppUser;
import com.authentication.Authenitication.exception.AppException;
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
    public Otp generateOtp(AppUser user, OtpPurpose purpose) {

        otpRepository.invalidateActiveOtp(user.getId(), purpose);

        String otpValue = OtpUtil.generateOtp();

        Otp otp = new Otp();
        otp.setUser(user);
        otp.setPurpose(purpose);
        otp.setOtpValue(otpValue);
        otp.setCreatedAt(Instant.now());
        otp.setExpiryTime(
                Instant.now().plus(purpose.getExpiryMinutes(), ChronoUnit.MINUTES)
        );
        otp.setAttemptCount(0);
        otp.setMaxAttempts(purpose.getMaxAttempts());
        otp.setUsed(false);

        return otpRepository.save(otp);
    }
    @Transactional(noRollbackFor = AppException.class)
    public void verifyOtp(AppUser user, OtpPurpose purpose, String inputOtp) {

        Otp otp = otpRepository.findActiveOtp(user.getId(), purpose)
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
    public void otpResentLimitCheck(AppUser user, OtpPurpose purpose){
        Instant now = Instant.now();

        Otp latestOtp=otpRepository
                .findTopByUserIdAndPurposeOrderByCreatedAtDesc(user.getId(),purpose).
                orElse(null);

        if (latestOtp != null &&
                latestOtp.getCreatedAt().isAfter(now.minusSeconds(60))) {
            throw new AppException("AUTH_015");
        }

        long last10MinCount = otpRepository
                .countRecentOtps(
                        user.getId(),
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
