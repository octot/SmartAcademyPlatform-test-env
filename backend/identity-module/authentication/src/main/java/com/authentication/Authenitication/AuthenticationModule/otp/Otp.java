package com.authentication.Authenitication.AuthenticationModule.otp;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.otp.enums.VerificationChannel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "otp")
@Getter
@Setter
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String target;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationChannel channel;

    @Column(name = "otp_value", nullable = false)
    private String otpValue;


    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private OtpPurpose purpose;

    private Instant expiryTime;

    private int attemptCount;

    private int maxAttempts;

    private boolean isUsed;

    private Instant createdAt;


}
