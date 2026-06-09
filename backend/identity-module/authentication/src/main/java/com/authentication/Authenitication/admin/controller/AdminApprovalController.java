package com.authentication.Authenitication.admin.controller;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.security.SecurityUtils;
import com.authentication.Authenitication.admin.dto.RejectAdminRequestRequest;
import com.authentication.Authenitication.admin.entity.AdminRequestResponse;
import com.authentication.Authenitication.admin.enums.ApprovalStatus;
import com.authentication.Authenitication.admin.service.AdminApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AdminApprovalController {

    private final AdminApprovalService adminApprovalService;
    private final SecurityUtils securityUtils;

    @PreAuthorize("hasAuthority('ADMIN_REQUEST_VIEW_GLOBAL')")
    @GetMapping("/admin-requests")
    public ResponseEntity<Page<AdminRequestResponse>> getAdminRequests
            (@PageableDefault(
                     sort = "requestedAt",
                     direction = Sort.Direction.DESC
             ) @RequestParam(required = false)
             ApprovalStatus status,
             @RequestParam(defaultValue = "")
             String search,
             Pageable pageable) {
        return ResponseEntity.ok(adminApprovalService.getAdminRequests(status, search, pageable));
    }

    @PreAuthorize("hasAuthority('ADMIN_REQUEST_APPROVE_GLOBAL')")
    @PatchMapping("/admin-requests/{id}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable UUID id
    ) {
        AppUser currentUser = securityUtils.getCurrentUser();
        adminApprovalService.approveRequest(
                id,
                currentUser
        );
        return ResponseEntity.ok(
                "Admin request approved successfully"
        );
    }

    @PreAuthorize("hasAuthority('ADMIN_REQUEST_APPROVE_GLOBAL')")
    @PatchMapping("/admin-requests/{id}/reject")
    public ResponseEntity<String> rejectRequest(
            @PathVariable UUID id,
            @RequestBody
            RejectAdminRequestRequest rejectRequest
    ) {

        AppUser currentUser = securityUtils.getCurrentUser();
        adminApprovalService.rejectRequest(
                id,
                currentUser
                , rejectRequest
        );

        return ResponseEntity.ok(
                "Admin request rejected successfully"
        );
    }

}
