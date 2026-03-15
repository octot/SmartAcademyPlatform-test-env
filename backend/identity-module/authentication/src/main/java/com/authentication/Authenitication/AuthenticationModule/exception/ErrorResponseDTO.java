package com.authentication.Authenitication.AuthenticationModule.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String path;
}
