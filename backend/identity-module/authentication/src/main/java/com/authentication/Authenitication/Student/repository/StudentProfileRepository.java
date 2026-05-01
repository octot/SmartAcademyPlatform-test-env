package com.authentication.Authenitication.Student.repository;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.Student.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, UUID> {

    boolean existsByUser(AppUser user);

    Optional<StudentProfile> findByUserId(UUID id);
}
