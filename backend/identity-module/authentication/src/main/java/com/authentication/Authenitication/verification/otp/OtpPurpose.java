package com.authentication.Authenitication.verification.otp;


import lombok.Getter;

//Policy driven enum
@Getter
public enum OtpPurpose {
    SIGNUP(10, 5),
    PASSWORD_RESET(5, 3),
    PROFILE_UPDATE(5, 3),
    EMAIL_CHANGE(10, 5);

    private final int expiryMinutes;
    private final int maxAttempts;


    OtpPurpose(int expiryMinutes, int maxAttempts) {
        this.expiryMinutes = expiryMinutes;
        this.maxAttempts = maxAttempts;
    }

}
