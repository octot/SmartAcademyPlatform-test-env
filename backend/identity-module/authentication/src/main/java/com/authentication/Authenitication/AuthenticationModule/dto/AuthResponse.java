package com.authentication.Authenitication.AuthenticationModule.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private List<String> roles;
    private String activeRole;

    public AuthResponse(String token, List<String> roles, String activeRole) {
        this.token = token;
        this.roles = roles;
        this.activeRole = activeRole;
    }

}
