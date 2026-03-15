package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.Authorization.entity.Role;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.Authorization.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getDefaultUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(() -> new AppException("ROLE_001"));
    }

    public Role getAdminUserRole() {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new AppException("ROLE_002"));
    }

}
