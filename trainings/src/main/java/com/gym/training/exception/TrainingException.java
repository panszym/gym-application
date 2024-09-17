package com.gym.training.exception;

public class TrainingException extends RuntimeException {
    private Error error;

    public TrainingException(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
