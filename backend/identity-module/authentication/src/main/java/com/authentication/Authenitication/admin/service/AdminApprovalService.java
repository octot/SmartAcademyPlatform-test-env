package com.authentication.Authenitication.admin.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.admin.dto.RejectAdminRequestRequest;
import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.entity.AdminRequestResponse;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import com.authentication.Authenitication.admin.repository.AdminRegistrationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminApprovalService {

    private final AdminRegistrationRequestRepository
            adminRequestRepository;

    private final AdminProvisioningService
            adminProvisioningService;


    @Transactional(readOnly = true)
    public Page<AdminRequestResponse> getAdminRequests(
            ApprovalStatus status,
            String search,
            Pageable pageable) {
        if (status == null) {
            return adminRequestRepository.findAll(pageable).map(this::toResponse);
        }
        return adminRequestRepository
                .searchRequests(
                        status,
                        search,
                        pageable
                ).map(this::toResponse);
    }

    public void approveRequest(
            UUID requestId,
            AppUser reviewer
    ) {

        AdminRegistrationRequest request =
                getAdminRegistrationRequestOrThrow(requestId);

        validateApprovalRequest(request);

        adminProvisioningService
                .provisionAdmin(request);

        markApproved(request, reviewer);

        adminRequestRepository.save(request);
    }

    private void validateApprovalRequest(
            AdminRegistrationRequest request
    ) {

        if (request.getStatus() != ApprovalStatus.PENDING) {
            throw new AppException(
                    "ADMIN_REQ_005"
            );
        }

        if (!request.isEmailVerified()) {
            throw new AppException(
                    "ADMIN_REQ_006"
            );
        }
    }

    private AdminRegistrationRequest getAdminRegistrationRequestOrThrow(UUID requestId) {

        return adminRequestRepository
                .findById(requestId)
                .orElseThrow(
                        () -> new AppException(
                                "ADMIN_REQ_003"
                        )
                );
    }

    private void markApproved(
            AdminRegistrationRequest request,
            AppUser reviewer
    ) {

        request.setStatus(
                ApprovalStatus.APPROVED
        );

        request.setReviewedAt(
                LocalDateTime.now()
        );

        request.setReviewedBy(reviewer);
    }

    public void rejectRequest(
            UUID requestId,
            AppUser reviewer,
            RejectAdminRequestRequest rejectRequest
    ) {

        AdminRegistrationRequest request =
                getAdminRegistrationRequestOrThrow(requestId);

        if (request.getStatus() != ApprovalStatus.PENDING) {
            throw new AppException(
                    "ADMIN_REQ_005"
            );
        }

        request.setStatus(
                ApprovalStatus.REJECTED
        );

        request.setReviewedAt(
                LocalDateTime.now()
        );

        request.setReviewedBy(reviewer);

        if (rejectRequest.reason() == null) {

            throw new AppException(
                    "ADMIN_REQ_0010"
            );
        }

        request.setRejectionReason(
                rejectRequest.reason()
        );

        request.setRejectionComment(
                rejectRequest.comment()
        );


        adminRequestRepository.save(request);
    }

    private AdminRequestResponse toResponse(
            AdminRegistrationRequest request
    ) {
        return new AdminRequestResponse(
                request.getId(),
                request.getUsername(),
                request.getEmail(),
                request.getPurpose(),
                request.getDescription(),
                request.getStatus(),
                request.getRequestedAt(),
                request.getReviewedAt(),
                request.getReviewedBy() != null
                        ? request.getReviewedBy().getUsername()
                        : null,
                request.isEmailVerified(),
                request.getRejectionReason(),
                request.getRejectionComment()
        );
    }
}
