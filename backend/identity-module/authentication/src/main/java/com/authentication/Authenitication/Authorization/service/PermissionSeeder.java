package com.authentication.Authenitication.Authorization.service;

import com.authentication.Authenitication.Authorization.Enum.Action;
import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.Enum.Scope;
import com.authentication.Authenitication.Authorization.entity.Permission;
import com.authentication.Authenitication.Authorization.repository.PermissionRepository;
import com.authentication.Authenitication.Authorization.repository.RoleRepository;
import com.authentication.Authenitication.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Order(3)
public class PermissionSeeder implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public PermissionSeeder(PermissionRepository permissionRepository,
                            RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        seedPermissions();
        assignPermissionsToRoles();
    }

    private void seedPermissions() {

        if (permissionRepository.count() > 0) return;

        createPermission(Resource.USER, Action.VIEW, null, "View users");
        createPermission(Resource.USER, Action.APPROVE, null, "Approve users");
        createPermission(Resource.ADMIN, Action.CREATE, null, " Create admin");
        createPermission(Resource.ADMIN, Action.VIEW, null, "View admin");
    }

    private void createPermission(Resource r, Action a, Scope s, String desc) {
        Permission p = new Permission();
        p.setResource(r);
        p.setAction(a);
        p.setScope(s);
        p.setDescription(desc);

        permissionRepository.save(p);
    }

    private void assignPermissionsToRoles() {

        Role admin = roleRepository.findByName(RoleName.ADMIN).orElseThrow();
        Role tutor = roleRepository.findByName(RoleName.TUTOR).orElseThrow();
        Role superAdmin = roleRepository.findByName(RoleName.SUPER_ADMIN).orElseThrow();
        Role student = roleRepository.findByName(RoleName.STUDENT).orElseThrow();
        Permission userView = permissionRepository
                .findByResourceAndActionAndScope(Resource.USER, Action.VIEW, null)
                .orElseThrow();

        Permission userApprove = permissionRepository
                .findByResourceAndActionAndScope(Resource.USER, Action.APPROVE, null)
                .orElseThrow();

        Permission adminCreate = permissionRepository
                .findByResourceAndActionAndScope(Resource.ADMIN, Action.CREATE, null)
                .orElseThrow();

        Permission adminView = permissionRepository
                .findByResourceAndActionAndScope(Resource.ADMIN, Action.VIEW, null)
                .orElseThrow();

        Set<Permission> allPermissions = new HashSet<>(permissionRepository.findAll());
        superAdmin.setPermissions(allPermissions);

        admin.getPermissions().addAll(Set.of(
                userView,
                userApprove,
                adminCreate,
                adminView
        ));
        tutor.getPermissions().add(userView);
        student.getPermissions().add(userView);
        roleRepository.save(superAdmin);
        roleRepository.save(admin);
        roleRepository.save(tutor);
        roleRepository.save(student);
    }

}

