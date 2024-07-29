package com.gym.training.exception;

public enum Error {
    TRAINING_NOT_FOUND("Training is not in the our system."),
    TRAINING_ALREADY_EXIST("Training already exist in our system"),
    MORE_PARTICIPANTS_THAN_LIMIT("More participants than limit in that training"),
    TRAINING_CANNOT_HAVE_ACTIVE_STATUS("Training can not have a active status. We don't have place on this training"),
    TRAINING_IS_NOT_ACTIVE("Training is not active"),
    CLIENT_IS_NOT_ACTIVE("Client is not active"),
    CLIENT_DOES_NOT_HAVE_PREMIUM_OR_MASTER_TICKET("Client does not have premium or master ticket.");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
