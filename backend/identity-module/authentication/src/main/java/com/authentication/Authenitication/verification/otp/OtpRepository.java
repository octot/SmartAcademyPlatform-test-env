package com.authentication.Authenitication.verification.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query("""
                SELECT o FROM Otp o
                WHERE o.user.id = :userId
                  AND o.purpose = :purpose
                  AND o.isUsed = false
                ORDER BY o.createdAt DESC
            """)
    Optional<Otp> findActiveOtp(
            @Param("userId") Long userId,
            @Param("purpose") OtpPurpose purpose
    );


    //To avoid old otps invalidate old unused ones before creating new ones
    @Modifying  //Required for update and delete queries
    @Query("""
            UPDATE Otp o
            SET o.isUsed = true
            WHERE o.user.id = :userId
              AND o.purpose = :purpose
              AND o.isUsed = false
            """)
    void invalidateActiveOtp(@Param("userId") Long userId, @Param("purpose") OtpPurpose otpPurpose);


    @Query("""
       SELECT COUNT(o)
       FROM Otp o
       WHERE o.user.id = :userId
         AND o.purpose = :purpose
         AND o.createdAt > :time
       """)
    long countRecentOtps(
            Long userId,
            OtpPurpose purpose,
            Instant time
    );
    Optional<Otp> findTopByUserIdAndPurposeOrderByCreatedAtDesc(
            Long userId,
            OtpPurpose purpose
    );



}
