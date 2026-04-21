package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorizationRepository extends JpaRepository<UserRole, UUID> {
    @Query("""
    SELECT COUNT(p)
    FROM UserRole ur, RolePermission rp, Permission p
    WHERE ur.role.id = rp.role.id
    AND rp.permission.id = p.id
    AND ur.user.id = :userId
    AND p.name = :permissionName
""")
    long countByUserIdAndPermissionName(UUID userId, String permissionName);
}
