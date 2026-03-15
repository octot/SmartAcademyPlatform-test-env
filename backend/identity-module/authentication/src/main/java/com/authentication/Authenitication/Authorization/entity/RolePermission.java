package com.authentication.Authenitication.Authorization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="role_permissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(RolePermissionId.class)
public class RolePermission {

    @Id
    @Column(name="role_id")
    private Long roleId;

    @Id
    @Column(name="permission_id")
    private Long permissionId;
}
