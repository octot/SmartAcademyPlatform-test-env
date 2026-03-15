package com.authentication.Authenitication.Authorization.service;


import com.authentication.Authenitication.Authorization.repository.RolePermissionRepository;
import com.authentication.Authenitication.Authorization.repository.UserRoleRepository;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public AuthorizationServiceImpl(UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository) {
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public void authorize(Long userId, String permission) {

     List<Long> roleIds=userRoleRepository.findRoleIdsByUserId(userId);

     if(roleIds.isEmpty()){
        throw new AppException("ROLE_001");
     }
     List<String>permissions=rolePermissionRepository.findPermissionNamesByRoleId(roleIds);

     if(permissions.isEmpty()){
            throw new AppException("ROLE_001");
        }
    }
}
