package com.authentication.Authenitication.AuthenticationModule.repository;


import com.authentication.Authenitication.AuthenticationModule.entity.EmailChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailChangeRepository  extends JpaRepository<EmailChangeRequest,UUID> {

    Optional<EmailChangeRequest> findByUserIdAndVerifiedFalse(UUID userId);

    void deleteByUserId(UUID userId);


}
