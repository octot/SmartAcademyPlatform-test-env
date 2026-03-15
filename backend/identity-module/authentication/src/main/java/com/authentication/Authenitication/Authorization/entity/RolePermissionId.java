package com.authentication.Authenitication.Authorization.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {

    private Long roleId;
    private Long permissionId;

}
