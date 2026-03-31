package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.Authorization.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<UserRole, Long> {
    @Query("""
    SELECT COUNT(p)
    FROM UserRole ur, RolePermission rp, Permission p
    WHERE ur.roleId = rp.roleId
    AND rp.permissionId = p.id
    AND ur.userId = :userId
    AND p.name = :permissionName
""")
    long countByUserIdAndPermissionName(Long userId, String permissionName);
}
