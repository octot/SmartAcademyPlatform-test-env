package com.authentication.Authenitication.AuthenticationModule.exception;


import lombok.Data;

@Data
public class ErrorDetails {
    private String message;
    private int status;
}
