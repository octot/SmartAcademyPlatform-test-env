package com.authentication.Authenitication.AuthenticationModule.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query("""
                SELECT o FROM Otp o
                WHERE o.target = :target
                  AND o.purpose = :purpose
                  AND o.isUsed = false
                ORDER BY o.createdAt DESC
            """)
    Optional<Otp> findActiveOtp(
            @Param("target") String target,
            @Param("purpose") OtpPurpose purpose
    );


    //To avoid old otps invalidate old unused ones before creating new ones
    @Modifying  //Required for update and delete queries
    @Query("""
            UPDATE Otp o
            SET o.isUsed = true
            WHERE o.target = :target
              AND o.purpose = :purpose
              AND o.isUsed = false
            """)
    void invalidateActiveOtp(@Param("target") String target, @Param("purpose") OtpPurpose otpPurpose);


    @Query("""
       SELECT COUNT(o)
       FROM Otp o
       WHERE o.target = :target
         AND o.purpose = :purpose
         AND o.createdAt > :time
       """)
    long countRecentOtps(
            String target,
            OtpPurpose purpose,
            Instant time
    );
    Optional<Otp> findTopByTargetAndPurposeOrderByCreatedAtDesc(
            String target,
            OtpPurpose purpose
    );



}
