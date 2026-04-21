package com.authentication.Authenitication.admin.entity;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.department.entity.Department;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "admin_profiles")
public class AdminProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    @ManyToMany
    @JoinTable(name = "admin_departments", joinColumns = @JoinColumn(name = "admin_profile_id")
            , inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Set<Department> departments = new HashSet<>();


}
