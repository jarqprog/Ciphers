package com.jarq.app.exceptions;

public class InvalidKey extends Exception {

    private String message;

    public InvalidKey() {
        message = "invalid key!";
    }

    public String getMessage() {
        return message;
    }
}
