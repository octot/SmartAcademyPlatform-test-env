package com.authentication.Authenitication.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final String errorCode;

    public AppException(String errorCode) {
        this.errorCode = errorCode;
    }

}
