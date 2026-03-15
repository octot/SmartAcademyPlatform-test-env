package com.authentication.Authenitication.Authorization.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole
{
    @Id
    @Column(name="user_id")
    private Long userId;

    @Id
    @Column(name="role_id")
    private Long roleId;

}
