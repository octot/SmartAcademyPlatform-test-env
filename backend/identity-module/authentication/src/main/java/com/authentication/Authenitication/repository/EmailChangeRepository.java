package com.authentication.Authenitication.repository;


import com.authentication.Authenitication.entity.EmailChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailChangeRepository  extends JpaRepository<EmailChangeRequest,Long> {

    Optional<EmailChangeRequest> findByUserIdAndVerifiedFalse(Long userId);

    void deleteByUserId(Long userId);


}
