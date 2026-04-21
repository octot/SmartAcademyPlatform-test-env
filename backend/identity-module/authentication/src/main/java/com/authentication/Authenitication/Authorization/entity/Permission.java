package com.authentication.Authenitication.Authorization.entity;


import com.authentication.Authenitication.Authorization.Enum.Action;
import com.authentication.Authenitication.Authorization.Enum.Resource;
import com.authentication.Authenitication.Authorization.Enum.Scope;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_permission_unique",
                        columnNames = {"resource", "action", "scope"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Resource resource;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 50)
    private Scope scope;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.name = generateName();
    }

    private String generateName() {
        if (scope == null) {
            return resource.name() + "_" + action.name();
        }
        return resource.name() + "_" + action.name() + "_" + scope.name();
    }
}
