package com.authentication.Authenitication.Authorization.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long createdBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
