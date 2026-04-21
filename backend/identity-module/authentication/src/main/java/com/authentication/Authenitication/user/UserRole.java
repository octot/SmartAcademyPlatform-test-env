package com.authentication.Authenitication.user;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.role.Role;
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
public class UserRole
{
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
}
