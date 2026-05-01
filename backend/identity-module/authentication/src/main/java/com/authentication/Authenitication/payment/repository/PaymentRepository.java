package com.authentication.Authenitication.payment.repository;

import com.authentication.Authenitication.Tutor.entity.TutorPaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<TutorPaymentDetails, UUID> {

    Optional<TutorPaymentDetails> findByTutorId(UUID id);
}
