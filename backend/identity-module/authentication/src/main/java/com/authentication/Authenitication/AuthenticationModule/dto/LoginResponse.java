package com.authentication.Authenitication.AuthenticationModule.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private UserDto user;
    List<String> permissions;
    List<String> roles;
}
