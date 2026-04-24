package com.authentication.Authenitication.AuthenticationModule.entity;

import com.authentication.Authenitication.department.entity.Department;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.AuthenticationModule.enums.UserStatus;
import com.authentication.Authenitication.user.entity.UserProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;



    @Column(nullable = false)
    private boolean emailVerified;


    // true → account NOT locked
    // false → account locked
    private boolean accountNonBlocked = true;

    @Column(nullable = false)
    private Integer tokenVersion = 0;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (name = "user_roles", joinColumns =
            @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;
}
