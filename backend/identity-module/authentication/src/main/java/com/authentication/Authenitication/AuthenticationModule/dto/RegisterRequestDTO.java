package com.authentication.Authenitication.AuthenticationModule.dto;

import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterRequestDTO {


    // getters & setters
    private String username;
    private String password;
    private String email;
    private RoleName role;

    // REQUIRED: no-args constructor (for Jackson)
    public RegisterRequestDTO() {
    }

}
