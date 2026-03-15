package com.authentication.Authenitication.AuthenticationModule.util;

import com.authentication.Authenitication.AuthenticationModule.exception.AppException;

import java.util.regex.Pattern;

public class UsernameValidator {

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-z][a-z0-9._]{2,29}$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    public static void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new AppException("VAL_001");
        }

        if (username.contains("@")) {
            throw new AppException("VAL_002");
        }

        if (EMAIL_PATTERN.matcher(username).matches()) {
            throw new AppException("VAL_003");
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new AppException("VAL_003");
        }


    }

}
