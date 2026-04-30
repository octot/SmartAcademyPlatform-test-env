package com.authentication.Authenitication.user.dto;

import com.authentication.Authenitication.Authorization.Enum.RoleName;
import lombok.Getter;

import java.util.Map;

@Getter
public class ProfileSetupRequest {
    private RoleName role;

    //TODO need to seperate DTO per role wise
    private Map<String, Object> data;
}
