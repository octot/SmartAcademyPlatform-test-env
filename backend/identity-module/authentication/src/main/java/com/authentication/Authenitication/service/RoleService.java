package com.authentication.Authenitication.service;


import com.authentication.Authenitication.entity.RoleEntity;
import com.authentication.Authenitication.exception.AppException;
import com.authentication.Authenitication.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity getDefaultUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(() -> new AppException("ROLE_001"));
    }

    public RoleEntity getAdminUserRole() {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new AppException("ROLE_002"));
    }

}
