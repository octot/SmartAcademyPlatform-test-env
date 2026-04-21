package com.authentication.Authenitication.Authorization.repository;

import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleName name);
}
