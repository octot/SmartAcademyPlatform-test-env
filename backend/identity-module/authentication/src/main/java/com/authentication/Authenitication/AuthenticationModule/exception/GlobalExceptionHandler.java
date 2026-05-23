package com.authentication.Authenitication.AuthenticationModule.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorProperties errorProperties;


    //    malformed JSON ,invalid enum ,wrong datatype broken request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleJsonError(
            HttpServletRequest request) {
        return buildErrorResponse(
                "REQ_001",
                null,
                request
        );
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppException(
            AppException ex,
            HttpServletRequest request) {
        String finalCode = ex.getErrorCode();
        return buildErrorResponse(
                finalCode,
                ex.getField(),
                request
        );
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            String errorCode,
            String field,
            HttpServletRequest request
    ) {

        ErrorDetails errorDetails =
                errorProperties.getError(errorCode);

        if (errorDetails == null) {

            log.error("Undefined error code used: {}", errorCode);

            errorDetails =
                    errorProperties.getError("GEN_001");

            errorCode = "GEN_001";
        }

        ValidationErrorDTO error =
                ValidationErrorDTO.builder()
                        .code(errorCode)
                        .field(field)
                        .message(errorDetails.getMessage())
                        .build();

        ErrorResponseDTO response =
                ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .success(false)
                        .path(request.getRequestURI())
                        .errors(List.of(error))
                        .build();

        return ResponseEntity
                .status(errorDetails.getStatus())
                .body(response);
    }

    //Bean validation  errors like email username not blank
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<ValidationErrorDTO> errors =
                extractValidationErrors(ex);

        ErrorResponseDTO response =
                buildValidationResponse(errors, request);

        return ResponseEntity.badRequest().body(response);
    }
    private List<ValidationErrorDTO> extractValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .toList();
    }
    private ValidationErrorDTO mapFieldError(
            FieldError fieldError
    ) {

        return ValidationErrorDTO.builder()
                .code("FIELD_001")
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }
    private ErrorResponseDTO buildValidationResponse(
            List<ValidationErrorDTO> errors,
            HttpServletRequest request
    ) {

        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .path(request.getRequestURI())
                .errors(errors)
                .build();
    }
}
