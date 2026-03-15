package com.authentication.Authenitication.AuthenticationModule.repository;


import com.authentication.Authenitication.AuthenticationModule.entity.EmailChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailChangeRepository  extends JpaRepository<EmailChangeRequest,Long> {

    Optional<EmailChangeRequest> findByUserIdAndVerifiedFalse(Long userId);

    void deleteByUserId(Long userId);


}
