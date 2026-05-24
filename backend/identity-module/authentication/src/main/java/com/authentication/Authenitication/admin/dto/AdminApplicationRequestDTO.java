package com.authentication.Authenitication.admin.dto;


import com.authentication.Authenitication.admin.enums.AdminRequestPurpose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminApplicationRequestDTO {
    private String username;

    private String email;

    private String password;

    private String description;

    private AdminRequestPurpose purpose;

}
