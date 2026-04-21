package com.authentication.Authenitication.AuthenticationModule.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserDto {

    private UUID id;
    private String name;

    public UserDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
