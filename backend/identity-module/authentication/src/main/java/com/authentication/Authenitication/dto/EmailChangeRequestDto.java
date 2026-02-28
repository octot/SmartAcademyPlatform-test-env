package com.authentication.Authenitication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailChangeRequestDto {
    @Email
    @NotBlank
    private String newEmail;

    @NotBlank
    private String password;

}
