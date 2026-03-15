package com.authentication.Authenitication.AuthenticationModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    private String login;   // username OR email
    private String password;
}
