package com.authentication.Authenitication.Authorization.service;


import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import com.authentication.Authenitication.Authorization.Enum.Scope;
import com.authentication.Authenitication.Authorization.Validator.BoundaryValidator;
import com.authentication.Authenitication.Authorization.Validator.BoundaryValidatorRegistry;
import com.authentication.Authenitication.Authorization.entity.Permission;
import com.authentication.Authenitication.Authorization.repository.AuthorizationRepository;
import com.authentication.Authenitication.Authorization.repository.PermissionRepository;
import com.authentication.Authenitication.Authorization.repository.RolePermissionRepository;
import com.authentication.Authenitication.Authorization.repository.UserRoleRepository;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{

    private static final Logger log =
            LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    private final AuthorizationRepository authorizationRepository;
    private final PermissionRepository permissionRepository;
    private final BoundaryValidatorRegistry validatorRegistry;
    public AuthorizationServiceImpl(UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository, AuthorizationRepository authorizationRepository, PermissionRepository permissionRepository, BoundaryValidatorRegistry validatorRegistry) {
        this.authorizationRepository = authorizationRepository;
        this.permissionRepository = permissionRepository;
        this.validatorRegistry = validatorRegistry;
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
    public void authorize(String permission) {
        authorize(permission, null);
    }


    @Override
    public void authorize(String permission, Long resourceId) {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        Long userId = userDetails.getUser().getId();
        boolean allowed =
                authorizationRepository
                        .existsByUserIdAndPermissionName(userId, permission);

        if (!allowed) {
            log.warn("AUTH_DENIED | userId={} | permission={}",
                    userId, permission);
            throw new AppException(
                    "AUTH_403"
            );
        }

        Permission permissionEntity=
                permissionRepository.findByName(permission)
                        .orElseThrow(()->
                                new AppException("AUTH_INVALID_PERMISSION"));

        Scope scope=permissionEntity.getScope();
        if(scope == Scope.OWN || scope == Scope.ASSIGNED){
            if(resourceId==null){
                throw new AppException("RESOURCE_REQUIRED");
            }
            BoundaryValidator validator=validatorRegistry.getValidator(permissionEntity.getResource());
            validator.validate(userId,resourceId,scope);

        }
        log.info("AUTH_ALLOWED | userId={} | permission={}",userId,permission);

    }
}
