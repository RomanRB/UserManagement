package com.sigma.users.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {

    public static final String USER_NOT_FOUND = "No such user found";
    public static final String USER_ALREADY_EXISTS = "Such user already exists";

    private final Date timestamp;
    private final String message;

    @Override
    public String toString() {
        return "{\n" +
                "\"timestamp\":\"" + timestamp + "\",\n " +
                "\"message\":\"" + message + "\"\n" +
                "}";
    }
}
