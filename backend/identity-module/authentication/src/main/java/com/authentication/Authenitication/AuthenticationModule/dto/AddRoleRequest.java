package com.authentication.Authenitication.AuthenticationModule.dto;

import com.authentication.Authenitication.Authorization.Enum.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddRoleRequest {
    @NotNull
    private RoleName role;
}
