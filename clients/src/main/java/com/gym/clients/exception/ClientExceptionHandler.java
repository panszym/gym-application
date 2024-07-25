package com.gym.clients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value = ClientException.class)
    public ResponseEntity<ErrorInfo> handleException(ClientException e){
        ResponseEntity response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if (Error.STUDENT_NOT_FOUND.equals(e.getError())){
              response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getError().getMessage()));
        }
        if (Error.EMAIL_ALREADY_EXIST.equals(e.getError())){
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getError().getMessage()));
        }
        return response;
    }
}
