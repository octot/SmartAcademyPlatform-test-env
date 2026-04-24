package com.authentication.Authenitication.Authorization.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.repository.RoleRepository;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.user.entity.UserProfile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Order(2)
public class SuperAdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminSeeder(UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedSuperAdmin() {

        String superAdminEmail = "superadmin@system.com";

        if (userRepository.findByProfile_Email(superAdminEmail).isEmpty()) {
            Role superAdminRole = roleRepository.findByName(RoleName.SUPER_ADMIN)
                    .orElseThrow(() -> new RuntimeException("SUPER_ADMIN role not found"));

            AppUser superAdmin = new AppUser();
            superAdmin.setUsername("superAdmin");
            superAdmin.setPassword(passwordEncoder.encode("Admin@123"));
            superAdmin.setEmailVerified(true);
            superAdmin.setRoles(Set.of(superAdminRole));

            superAdmin = userRepository.save(superAdmin);

            UserProfile userProfile=new UserProfile();
            userProfile.setUser(superAdmin);  //
            userProfile.setEmail(superAdminEmail);
            userProfile.setStatus(UserStatus.ACTIVE);


            superAdmin.setProfile(userProfile);

            userRepository.save(superAdmin);

            System.out.println("✅ SUPER_ADMIN created");
        } else {
            System.out.println("ℹ️ SUPER_ADMIN already exists");
        }
    }


    @Override
    public void run(String... args) {
        seedSuperAdmin();
    }
}
