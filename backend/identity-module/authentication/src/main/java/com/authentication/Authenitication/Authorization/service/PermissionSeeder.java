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

    // =========================
    // STEP 1: SEED PERMISSIONS
    // =========================
    private void seedPermissions() {

        if (permissionRepository.count() > 0) return;
        // USER → VIEW + EDIT (scoped)
        createScopedPermissions(Resource.USER, Action.VIEW, "View users");
        createScopedPermissions(Resource.USER, Action.EDIT, "Edit users");

        // ADMIN → only GLOBAL
        createPermission(Resource.ADMIN, Action.CREATE, Scope.GLOBAL, "Create admin");
        createPermission(Resource.ADMIN, Action.VIEW, Scope.GLOBAL, "View admin");
    }

    // Create OWN / DEPARTMENT / GLOBAL
    private void createScopedPermissions(Resource resource, Action action, String desc) {

        createPermission(resource, action, Scope.OWN, desc + " (OWN)");
        createPermission(resource, action, Scope.DEPARTMENT, desc + " (DEPARTMENT)");
        createPermission(resource, action, Scope.GLOBAL, desc + " (GLOBAL)");
    }


    private void createPermission(Resource r, Action a, Scope s, String desc) {
        Permission p = new Permission();
        p.setResource(r);
        p.setAction(a);
        p.setScope(s);
        p.setDescription(desc);

        permissionRepository.save(p);
    }

    // =========================
    // STEP 2: ASSIGN TO ROLES
    // =========================
    private void assignPermissionsToRoles() {

        Role superAdmin = roleRepository.findByName(RoleName.SUPER_ADMIN).orElseThrow();
        Role admin = roleRepository.findByName(RoleName.ADMIN).orElseThrow();
        Role tutor = roleRepository.findByName(RoleName.TUTOR).orElseThrow();
        Role student = roleRepository.findByName(RoleName.STUDENT).orElseThrow();

        // ===== Fetch Permissions =====

        // USER VIEW
        Permission userViewOwn = getPermission(Resource.USER, Action.VIEW, Scope.OWN);
        Permission userViewDept = getPermission(Resource.USER, Action.VIEW, Scope.DEPARTMENT);
        Permission userViewGlobal = getPermission(Resource.USER, Action.VIEW, Scope.GLOBAL);

        // USER EDIT
        Permission userEditOwn = getPermission(Resource.USER, Action.EDIT, Scope.OWN);
        Permission userEditDept = getPermission(Resource.USER, Action.EDIT, Scope.DEPARTMENT);
        Permission userEditGlobal = getPermission(Resource.USER, Action.EDIT, Scope.GLOBAL);

        // ADMIN
        Permission adminCreate = getPermission(Resource.ADMIN, Action.CREATE, Scope.GLOBAL);
        Permission adminView = getPermission(Resource.ADMIN, Action.VIEW, Scope.GLOBAL);

        // ===== SUPER ADMIN → ALL =====
        Set<Permission> allPermissions = new HashSet<>(permissionRepository.findAll());
        superAdmin.setPermissions(allPermissions);

        // ===== ADMIN → GLOBAL CONTROL =====
        admin.getPermissions().addAll(Set.of(
                userViewGlobal,
                userEditGlobal,
                adminCreate,
                adminView
        ));

        // ===== TUTOR → OWN ONLY =====
        tutor.getPermissions().addAll(Set.of(
                userViewOwn,
                userEditOwn
        ));

        // ===== STUDENT → VERY LIMITED =====
        student.getPermissions().add(userViewOwn);

        // ===== SAVE =====
        roleRepository.save(superAdmin);
        roleRepository.save(admin);
        roleRepository.save(tutor);
        roleRepository.save(student);
    }

    private Permission getPermission(Resource r, Action a, Scope s) {
        return permissionRepository
                .findByResourceAndActionAndScope(r, a, s)
                .orElseThrow();
    }

}

