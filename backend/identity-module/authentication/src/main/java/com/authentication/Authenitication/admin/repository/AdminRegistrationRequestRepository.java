package com.authentication.Authenitication.admin.repository;


import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRegistrationRequestRepository extends JpaRepository<AdminRegistrationRequest, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<AdminRegistrationRequest>
    findByStatus(ApprovalStatus status);

    Optional<AdminRegistrationRequest> findByEmail(String target);
}
