package com.authentication.Authenitication.Authorization.service;


import com.authentication.Authenitication.Authorization.repository.AuthorizationRepository;
import com.authentication.Authenitication.Authorization.repository.RolePermissionRepository;
import com.authentication.Authenitication.Authorization.repository.UserRoleRepository;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{

    private static final Logger log =
            LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    private final AuthorizationRepository authorizationRepository;

    public AuthorizationServiceImpl(UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository, AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    /*
    1. Fetch user
    2. Fetch roles of user
    3. Fetch permissions of those roles
    4. Check if permission string matches
    5. If yes → log ALLOWED
    6. If no → log DENIED + throw AccessDeniedException
     */
    @Override
    public void authorize(Long userId, String permission) {

        boolean allowed =
                authorizationRepository
                        .existsByUserIdAndPermissionName(userId, permission);

        if (!allowed) {
            log.warn("AUTH_DENIED | userId={} | permission={}",
                    userId, permission);
            throw new AppException(
                    "ROLE_001"
            );
        }
    }
}
