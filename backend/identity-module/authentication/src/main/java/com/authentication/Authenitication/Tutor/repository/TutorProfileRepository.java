package com.authentication.Authenitication.Tutor.repository;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.Student.entity.StudentProfile;
import com.authentication.Authenitication.Tutor.entity.TutorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TutorProfileRepository extends JpaRepository<TutorProfile, UUID> {

    boolean existsByUser(AppUser user);
}
