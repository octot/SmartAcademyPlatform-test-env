package com.authentication.Authenitication.admin.dto;

import com.authentication.Authenitication.admin.enums.RejectionReason;

public record RejectAdminRequestRequest(

        RejectionReason reason,

        String comment

) {
}