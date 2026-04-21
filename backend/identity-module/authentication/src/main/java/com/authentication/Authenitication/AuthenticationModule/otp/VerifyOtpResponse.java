package com.authentication.Authenitication.AuthenticationModule.otp;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpResponse {

    private String message;
    private String resetToken;

    public VerifyOtpResponse(String message) {
        this.message = message;
    }

    public VerifyOtpResponse(String message, String resetToken) {
        this.message = message;
        this.resetToken = resetToken;
    }

}
