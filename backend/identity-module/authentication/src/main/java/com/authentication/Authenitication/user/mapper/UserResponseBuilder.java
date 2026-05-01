package com.authentication.Authenitication.user.mapper;


import com.authentication.Authenitication.AuthenticationModule.dto.AuthUserResponse;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.service.PermissionService;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.user.service.ProfileService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserResponseBuilder {

    private final PermissionService permissionService;
    private final ProfileService profileService;

    public UserResponseBuilder(PermissionService permissionService, ProfileService profileService) {
        this.permissionService = permissionService;
        this.profileService = profileService;
    }

    public AuthUserResponse buildUserResponse(AppUser user) {

        Set<RoleName> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        RoleName activeRole = user.getActiveRole();

        Set<String> permissions =
                permissionService.getPermissionsByRole(user, activeRole);

        Map<RoleName, Boolean> profileCompleted =
                profileService.getProfileStatus(user);

        return new AuthUserResponse(
                user.getId(),
                user.getUsername(),
                roles,
                activeRole,
                permissions,
                profileCompleted
        );
    }
}
