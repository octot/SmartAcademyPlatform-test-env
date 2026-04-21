package com.authentication.Authenitication.Authorization.repository;

import com.authentication.Authenitication.Authorization.Enum.Action;
import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.Scope;
import com.authentication.Authenitication.Authorization.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Optional<Permission> findByName(String name);
    Optional<Permission> findByResourceAndActionAndScope(Resource r , Action a , Scope scope);
}
