package com.authentication.Authenitication.Authorization.repository;

import com.authentication.Authenitication.Authorization.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
