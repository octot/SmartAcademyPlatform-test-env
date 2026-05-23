package com.authentication.Authenitication.AuthenticationModule.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final String errorCode;

    private final String field;

    public AppException(String errorCode) {
        this.errorCode = errorCode;
        this.field = null;
    }

    public AppException(
            String errorCode,
            String field
    ) {
        this.errorCode = errorCode;
        this.field = field;
    }

}
