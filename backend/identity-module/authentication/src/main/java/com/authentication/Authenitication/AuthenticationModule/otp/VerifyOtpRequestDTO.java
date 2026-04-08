package com.authentication.Authenitication.AuthenticationModule.otp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequestDTO {

    private String login;
    private String otp;
    private OtpPurpose purpose;
}
