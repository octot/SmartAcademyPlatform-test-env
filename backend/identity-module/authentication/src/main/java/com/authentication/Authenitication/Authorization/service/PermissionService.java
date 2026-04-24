package com.authentication.Authenitication.Authorization.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.Authorization.Enum.Action;
import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private String buildPermission(Resource r, Action a, Scope s) {
        return r.name() + "_" + a.name() + "_" + s.name();
    }

    private boolean hasAuthority(Authentication auth, String permission) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(permission::equals);
    }

    // =========================
    // GENERIC ACCESS CHECK (NO DB CALLS)
    // =========================
    public boolean hasAccess(Resource resource,
                             Action action,
                             AppUser currentUser,
                             AppUser targetUser,
                             Authentication authentication) {

        // GLOBAL
        if (hasAuthority(authentication, buildPermission(resource, action, Scope.GLOBAL))) {
            return true;
        }

        // DEPARTMENT
        if (hasAuthority(authentication, buildPermission(resource, action, Scope.DEPARTMENT))) {
            return currentUser.getProfile().getDepartment().getId()
                    .equals(targetUser.getProfile().getDepartment().getId());
        }

        // OWN
        if (hasAuthority(authentication, buildPermission(resource, action, Scope.OWN))) {
            return currentUser.getId().equals(targetUser.getId());
        }

        return false;
    }

    public Scope resolveScope(Resource resource, Action action, Authentication auth) {

        if (hasAuthority(auth, buildPermission(resource, action, Scope.GLOBAL))) {
            return Scope.GLOBAL;
        }

        if (hasAuthority(auth, buildPermission(resource, action, Scope.DEPARTMENT))) {
            return Scope.DEPARTMENT;
        }

        if (hasAuthority(auth, buildPermission(resource, action, Scope.OWN))) {
            return Scope.OWN;
        }

        throw new AppException("PERMISSION_001");
    }


}
