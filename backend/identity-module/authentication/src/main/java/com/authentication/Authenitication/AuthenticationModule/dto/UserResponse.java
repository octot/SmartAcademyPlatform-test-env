package com.authentication.Authenitication.AuthenticationModule.dto;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.user.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.function.Consumer;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserResponse {
    private UUID id;
    private String username;
    private String email;

    private String fullName;
    private String mobile;
    private String address;

    private String department;

    private UserStatus status;
    public static UserResponse mapToResponse(AppUser user) {

        UserResponse res = new UserResponse();

        res.setId(user.getId());
        res.setUsername(user.getUsername());

        if (user.getProfile() != null) {

            UserProfile profile = user.getProfile();

            res.setEmail(profile.getEmail());
            res.setFullName(profile.getFullName());
            res.setMobile(profile.getMobile());
            res.setAddress(profile.getAddress());
            res.setStatus(profile.getStatus());

            if (profile.getDepartment() != null) {
                res.setDepartment(profile.getDepartment().getName());
            }
        }

        return res;
    }
}
