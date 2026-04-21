package com.authentication.Authenitication.user;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserRoleId implements Serializable {

    private UUID userId;
    private UUID roleId;

}
