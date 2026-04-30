package com.authentication.Authenitication.AuthenticationModule.dto;

import com.authentication.Authenitication.Authorization.Enum.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuthUserResponse {
    private UUID id;
    private String username;

    private Set<RoleName> roles;
    private RoleName activeRole;
    private Set<String> permissions;

    private Map<RoleName, Boolean> profileCompleted;

}
