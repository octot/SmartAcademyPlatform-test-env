package com.authentication.Authenitication.AuthenticationModule.entity;

import com.authentication.Authenitication.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserListResponse {
    UUID id;
    String username;
    String email;
    String status;
    boolean emailVerified;
    boolean accountNonBlocked;
    Set<Role> roles;
    public static UserListResponse from(AppUser user) {
        return new UserListResponse(
                user.getId(),
                user.getUsername(),
                user.getProfile().getEmail(),
                user.getProfile().getStatus().name(),
                user.isEmailVerified(),
                user.isAccountNonBlocked(),
                user.getRoles()
        );
    }
}
