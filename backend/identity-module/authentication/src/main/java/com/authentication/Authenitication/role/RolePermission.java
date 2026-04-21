package com.authentication.Authenitication.role;

import com.authentication.Authenitication.Authorization.entity.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_permissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne
    @MapsId("roleId")
    private Role role;

    @ManyToOne
    @MapsId("permissionId")
    private Permission permission;


}
