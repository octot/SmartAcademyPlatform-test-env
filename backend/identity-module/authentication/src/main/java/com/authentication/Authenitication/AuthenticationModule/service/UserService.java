package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.dto.UpdateUserRequest;
import com.authentication.Authenitication.AuthenticationModule.dto.UserResponse;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.entity.UserListResponse;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.Authorization.Enum.Action;
import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.Scope;
import com.authentication.Authenitication.Authorization.service.PermissionService;
import com.authentication.Authenitication.user.entity.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.authentication.Authenitication.AuthenticationModule.dto.UserResponse.mapToResponse;
import static com.authentication.Authenitication.Utiity.UtilityFunctions.updateIfNotNull;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public UserService(UserRepository userRepository, PermissionService permissionService) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    public AppUser findByUsername(String login) {
        AppUser user;
        if (login.contains("@")) {
            user = userRepository.findByProfile_Email(login).orElseThrow(() -> new AppException("AUTH_011"));
        } else {
            user = userRepository.findByUsername(login)
                    .orElseThrow(() -> new AppException("AUTH_011"));
        }
        if (!user.isEmailVerified()) {
            throw new AppException("AUTH_002");
        }
        if (user.getProfile().getStatus() != UserStatus.ACTIVE) {
            throw new AppException("AUTH_003");
        }
        return user;
    }

    public void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AppException("AUTH_006"); // Username already taken
        }
    }

    public List<UserListResponse> getUsers(Authentication auth) {
        Scope scope = permissionService.resolveScope(Resource.USER, Action.VIEW, auth);
        AppUser currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow();

        return switch (scope) {
            case GLOBAL -> userRepository.findAll()
                    .stream()
                    .map(UserListResponse::from)
                    .toList();
            case DEPARTMENT -> {
                if (currentUser.getProfile().getDepartment() == null) {
                    yield List.of();
                }

                yield userRepository
                        .findByProfile_Department_Id(
                                currentUser.getProfile().getDepartment().getId()
                        )
                        .stream()
                        .map(UserListResponse::from)
                        .toList();
            }

            case OWN -> List.of(UserListResponse.from(currentUser));
            default -> throw new AppException("ROLE_005");
        };
    }

    public UserResponse updateUser(String userName, UpdateUserRequest userDto, Authentication auth) {
        AppUser currentUser = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new AppException("USER_02")); //actor who performs the action
        AppUser targetUser = userRepository.findByUsername(userName).orElseThrow(() -> new AppException("USER_03"));

        if (!permissionService.hasAccess(
                Resource.USER,
                Action.EDIT,
                currentUser,
                targetUser,
                auth
        )) {
            throw new AppException("ROLE_001");
        }

        UserProfile profile = targetUser.getProfile();
        if (profile == null) {
            throw new AppException("USER_01");
        }
        updateIfNotNull(userDto.getFullName(), profile::setFullName);
        updateIfNotNull(userDto.getMobile(), profile::setMobile);
        updateIfNotNull(userDto.getAddress(), profile::setAddress);
        updateIfNotNull(userDto.getStatus(), profile::setStatus);
        userRepository.save(targetUser);
        return mapToResponse(targetUser);

    }

}
