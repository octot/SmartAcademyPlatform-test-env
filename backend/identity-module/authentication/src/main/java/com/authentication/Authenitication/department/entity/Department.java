package com.authentication.Authenitication.department.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "department")
@Getter
public class Department {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @Column(nullable =false)
    private String name;

}
