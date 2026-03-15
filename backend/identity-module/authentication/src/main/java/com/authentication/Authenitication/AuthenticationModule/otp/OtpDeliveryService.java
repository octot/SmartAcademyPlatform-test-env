package com.authentication.Authenitication.AuthenticationModule.otp;

public interface OtpDeliveryService {
    void sendOtp(String destination, String otp,long expiryTime);

}
