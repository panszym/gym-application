package com.gym.clients.exception;

public class ClientException extends RuntimeException{
    private Error error;

    public ClientException(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
