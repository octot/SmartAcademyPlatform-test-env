package com.authentication.Authenitication.Authorization.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {

    private Long userId;
    private Long roleId;
}
