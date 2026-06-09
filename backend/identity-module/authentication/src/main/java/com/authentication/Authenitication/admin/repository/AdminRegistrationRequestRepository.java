package com.authentication.Authenitication.admin.repository;


import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRegistrationRequestRepository extends JpaRepository<AdminRegistrationRequest, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    @Query("""
                SELECT r
                FROM AdminRegistrationRequest r
                WHERE
                    (:status IS NULL OR r.status = :status)
                    AND
                    (
                        :search = ''
                        OR LOWER(r.username)
                            LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(r.email)
                            LIKE LOWER(CONCAT('%', :search, '%'))
                    )
            """)
    Page<AdminRegistrationRequest> searchRequests(
            ApprovalStatus status,
            String search,
            Pageable pageable
    );

    Optional<AdminRegistrationRequest> findByEmail(String target);
}
