package com.authentication.Authenitication.department.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @Column(nullable =false)
    private String name;

}
