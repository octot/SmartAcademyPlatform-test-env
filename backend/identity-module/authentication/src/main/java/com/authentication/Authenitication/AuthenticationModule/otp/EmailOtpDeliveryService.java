package com.authentication.Authenitication.AuthenticationModule.otp;


import com.authentication.Authenitication.AuthenticationModule.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailOtpDeliveryService implements OtpDeliveryService {
    private final EmailService emailService;

    public EmailOtpDeliveryService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendOtp(String email, String otp, long expiryTime) {
        emailService.sendEmailOtp(email, otp,expiryTime);
    }
}
