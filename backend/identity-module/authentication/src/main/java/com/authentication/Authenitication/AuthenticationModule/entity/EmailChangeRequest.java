package com.authentication.Authenitication.AuthenticationModule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name="email_change_request")
public class EmailChangeRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name="new_email" ,nullable=false)
    private String newEmail;

    @Column(name="expiry_time" ,nullable=false)
    private Instant expiryTime;

    @Column(name="is_verified",nullable=false)
    private boolean verified;

}
