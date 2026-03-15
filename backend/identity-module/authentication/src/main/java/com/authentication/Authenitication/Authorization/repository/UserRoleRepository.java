package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.Authorization.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    @Query("SELECT ur.roleId from UserRole ur where ur.userId=:userId")
    List<Long> findRoleIdsByUserId(Long userId);

}
