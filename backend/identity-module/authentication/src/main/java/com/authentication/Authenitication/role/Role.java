package com.authentication.Authenitication.role;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Authorization.entity.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    @org.hibernate.annotations.UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private AppUser createdBy;

    public Role(long l, String user) {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions=new HashSet<>();


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
