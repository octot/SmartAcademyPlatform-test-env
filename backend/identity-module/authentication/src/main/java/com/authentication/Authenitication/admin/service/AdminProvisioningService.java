package com.authentication.Authenitication.admin.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.service.RoleService;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.admin.entity.AdminProfile;
import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.repository.AdminProfileRepository;
import com.authentication.Authenitication.user.entity.UserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/*
fetch pending requests
approve requests
reject requests
provision admin identity
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminProvisioningService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AdminProfileRepository adminProfileRepository;

    @Transactional
    public void provisionAdmin(
            AdminRegistrationRequest request
    ) {

        AppUser user = buildAdminUser(request);

        UserProfile profile = buildUserProfile(
                request,
                user
        );
        user.setProfile(profile);
        AdminProfile adminProfile =
                buildAdminProfile(user);
        userRepository.save(user);
        adminProfileRepository.save(adminProfile);

    }

    private UserProfile buildUserProfile(
            AdminRegistrationRequest request,
            AppUser user
    ) {

        UserProfile profile = new UserProfile();

        profile.setUser(user);

        profile.setEmail(request.getEmail());

        profile.setMobile(
                request.getPhoneNumber()
        );

        profile.setStatus(UserStatus.ACTIVE);

        return profile;
    }

    private AppUser buildAdminUser(
            AdminRegistrationRequest request
    ) {

        AppUser user = new AppUser();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRoles(
                Set.of(
                        roleService.getUserRole(
                                RoleName.ADMIN
                        )
                )
        );

        user.setActiveRole(RoleName.ADMIN);

        user.setEmailVerified(true);

        return user;
    }

    private AdminProfile buildAdminProfile(
            AppUser user
    ) {

        AdminProfile adminProfile =
                new AdminProfile();

        adminProfile.setUser(user);

        return adminProfile;
    }

}
