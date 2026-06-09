package com.authentication.Authenitication.admin.entity;

import com.authentication.Authenitication.admin.enums.AdminRequestPurpose;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import com.authentication.Authenitication.admin.enums.RejectionReason;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminRequestResponse(

        UUID id,

        String username,

        String email,

        AdminRequestPurpose purpose,

        String description,

        ApprovalStatus status,

        LocalDateTime requestedAt,

        LocalDateTime reviewedAt,

        String reviewedBy,

        Boolean emailVerified,

        RejectionReason rejectionReason,

        String rejectionComment

) {
}