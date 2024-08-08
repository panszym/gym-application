package com.gym.clients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value = ClientException.class)
    public ResponseEntity<ErrorInfo> handleException(ClientException e) {
        ResponseEntity response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if (Error.CLIENT_NOT_FOUND.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getError().getMessage()));
        }
        if (Error.EMAIL_ALREADY_EXIST.equals(e.getError()) || Error.DIFFERENT_EMAIL.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getError().getMessage()));
        }
        if (Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS.equals(e.getError())
                || Error.THERE_IS_NOT_REQUIRED_PARAMETER_TICKET.equals(e.getError())
                || Error.THERE_IS_NOT_THAT_EMAIL_IN_THE_SYSTEM.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getError().getMessage()));
        }
        if(Error.THERE_IS_NOT_YOUR_ACCOUNT.equals(e.getError())){
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(e.getError().getMessage()));
        }
        return response;
    }
}
