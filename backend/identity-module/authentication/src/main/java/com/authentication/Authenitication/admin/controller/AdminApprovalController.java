package com.authentication.Authenitication.admin.controller;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.security.SecurityUtils;
import com.authentication.Authenitication.admin.entity.AdminRegistrationRequest;
import com.authentication.Authenitication.admin.service.AdminApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AdminApprovalController {

    private final AdminApprovalService adminApprovalService;
    private final SecurityUtils securityUtils;

    //TODO Need input DTO here
    @PreAuthorize("hasAuthority('ADMIN_REQUEST_VIEW_GLOBAL')")
    @GetMapping("/admin-requests/pending")
    public ResponseEntity<List<AdminRegistrationRequest>> getPendingRequests() {
        return ResponseEntity.ok(adminApprovalService.getPendingRequests());
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
            @PathVariable UUID id
    ) {

        AppUser currentUser = securityUtils.getCurrentUser();
        adminApprovalService.rejectRequest(
                id,
                currentUser
        );

        return ResponseEntity.ok(
                "Admin request rejected successfully"
        );
    }

}
