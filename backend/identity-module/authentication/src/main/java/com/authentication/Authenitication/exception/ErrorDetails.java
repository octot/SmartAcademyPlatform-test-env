package com.authentication.Authenitication.exception;


import lombok.Data;

@Data
public class ErrorDetails {
    private String message;
    private int status;
}
