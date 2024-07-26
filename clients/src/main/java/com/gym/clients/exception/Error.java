package com.gym.clients.exception;

public enum Error {
    STUDENT_NOT_FOUND("Client is not in the our system."),
    EMAIL_ALREADY_EXIST("Email already exist in our system"),

    DIFFERENT_EMAIL("You can't change your email");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
