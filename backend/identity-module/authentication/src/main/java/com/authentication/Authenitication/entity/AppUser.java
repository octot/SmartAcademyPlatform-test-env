package com.authentication.Authenitication.entity;

import com.authentication.Authenitication.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean emailVerified;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

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
    private Set<RoleEntity> roles;


}
