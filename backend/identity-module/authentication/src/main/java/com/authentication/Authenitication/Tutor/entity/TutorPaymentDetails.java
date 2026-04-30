package com.authentication.Authenitication.Tutor.entity;

import com.authentication.Authenitication.Tutor.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tutor_payment")
@Getter
@Setter
public class TutorPaymentDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "tutor_id")
    private TutorProfile tutor;

    private PaymentMethod paymentMethod; // GPAY / BANK
    private String gpayNumber;
    private String accountHolderName;
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifscCode;
}
