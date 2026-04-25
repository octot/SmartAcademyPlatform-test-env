package com.authentication.Authenitication.AuthenticationModule.dto;


import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String fullName;
    private String mobile;
    private String address;
    private UserStatus status;
}
