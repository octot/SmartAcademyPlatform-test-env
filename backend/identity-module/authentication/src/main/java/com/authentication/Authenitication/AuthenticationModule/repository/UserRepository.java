package com.authentication.Authenitication.AuthenticationModule.repository;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByProfile_Email(String email);

    boolean existsByUsername(String username);

    boolean existsByProfile_Email(String email);

    List<AppUser> findByProfile_Department_Id(UUID departmentId);

}
