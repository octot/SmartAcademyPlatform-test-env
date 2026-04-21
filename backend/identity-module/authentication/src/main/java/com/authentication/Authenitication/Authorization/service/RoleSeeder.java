package com.authentication.Authenitication.Authorization.service;


import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.repository.RoleRepository;
import com.authentication.Authenitication.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void seedRoles() {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleRepository.save(role);
            });
        }
        System.out.println("✅ Roles seeded");

    }

    @Override
    public void run(String... args) {
        seedRoles();
    }
}
