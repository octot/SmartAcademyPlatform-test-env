package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.role.RolePermission;
import com.authentication.Authenitication.role.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {


    @Query("""
            SELECT p.name
            From RolePermission rp
            join Permission p on rp.permission.id=p.id
            where rp.role.id in :roleIds
            """)
    List<String>findPermissionNamesByRoleId(List<Long> roleIds);

}
