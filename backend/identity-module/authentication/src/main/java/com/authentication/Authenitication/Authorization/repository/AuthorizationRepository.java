package com.authentication.Authenitication.Authorization.repository;


import com.authentication.Authenitication.Authorization.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<UserRole, Long> {
    @Query("""
        SELECT COUNT(p) > 0
        FROM UserRole ur
        JOIN ur.role r
        JOIN r.permissions p
        WHERE ur.user.id = :userId
        AND p.name = :permission
    """)
    boolean existsByUserIdAndPermissionName(
            @Param("userId") Long userId,
            @Param("permission") String permission
    );
}
