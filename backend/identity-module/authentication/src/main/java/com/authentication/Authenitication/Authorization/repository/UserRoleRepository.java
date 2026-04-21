package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    @Query("SELECT ur.role.id from UserRole ur where ur.user.id=:userId")
    List<Long> findRoleIdsByUserId(Long userId);

}
