package com.gym.clients.exception;

public enum Error {
    CLIENT_NOT_FOUND("Client is not in the our system."),
    EMAIL_ALREADY_EXIST("Email already exist in our system"),

    DIFFERENT_EMAIL("You can't change your email"),
    THERE_IS_NOT_REQUIRED_PARAMETER_STATUS("Status parameter is required"),
    THERE_IS_NOT_REQUIRED_PARAMETER_TICKET("Ticket parameter is required");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
