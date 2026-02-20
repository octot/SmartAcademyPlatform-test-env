package com.authentication.Authenitication.repository;

import com.authentication.Authenitication.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


}
