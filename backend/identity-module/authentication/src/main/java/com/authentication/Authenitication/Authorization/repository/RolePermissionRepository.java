package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.Authorization.entity.RolePermission;
import com.authentication.Authenitication.Authorization.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {


    @Query("""
            SELECT p.name
            From RolePermission rp
            join Permission p on rp.permissionId=p.id
            where rp.roleId in :roleIds
            """)
    List<String>findPermissionNamesByRoleId(List<Long> roleIds);

}
