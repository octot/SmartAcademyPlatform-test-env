package com.authentication.Authenitication.AuthenticationModule.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDTO {


    // getters & setters
    private String username;
    private String password;
    private String email;

    // REQUIRED: no-args constructor (for Jackson)
    public RegisterRequestDTO() {
    }

    // Optional: all-args constructor
    public RegisterRequestDTO(String username, String password, String userEmail) {
        this.username = username;
        this.password = password;
        this.email = userEmail;
    }


}
