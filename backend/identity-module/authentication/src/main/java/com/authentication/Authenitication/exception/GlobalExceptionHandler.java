package com.authentication.Authenitication.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorProperties errorProperties;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppException(
            AppException ex,
            HttpServletRequest request) {

        ErrorDetails errorDetails =
                errorProperties.getError(ex.getErrorCode());
        String finalCode = ex.getErrorCode();
        if (errorDetails == null) {
            log.error("Undefined error code used: {}", ex.getErrorCode());
            errorDetails = errorProperties.getError("GEN_001");
            finalCode = "GEN_001";
        }

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(finalCode)
                .message(errorDetails.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(errorDetails.getStatus())
        );

    }

}
