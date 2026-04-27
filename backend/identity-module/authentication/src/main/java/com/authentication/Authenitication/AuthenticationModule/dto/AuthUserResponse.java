package com.authentication.Authenitication.AuthenticationModule.dto;

import com.authentication.Authenitication.Authorization.Enum.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuthUserResponse {
    private UUID id;
    private String username;
    private List<RoleName> roles;
    private List<String> permissions;
}
