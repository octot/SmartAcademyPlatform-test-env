package com.authentication.Authenitication.AuthenticationModule.otp;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

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
