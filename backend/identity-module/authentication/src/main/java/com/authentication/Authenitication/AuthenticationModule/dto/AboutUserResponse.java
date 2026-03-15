package com.authentication.Authenitication.AuthenticationModule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AboutUserResponse {

    private String username;
    private String email;
    private List<String> roles;

}
