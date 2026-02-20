package com.authentication.Authenitication.verification.otp;

public interface OtpDeliveryService {
    void sendOtp(String destination, String otp,long expiryTime);

}
