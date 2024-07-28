package com.gym.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrainingExceptionHandler {

    @ExceptionHandler(value = TrainingException.class)
    public ResponseEntity<ErrorInfo> handleException(TrainingException e) {
        ResponseEntity response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if (Error.TRAINING_NOT_FOUND.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getError().getMessage()));
        }
        if (Error.TRAINING_ALREADY_EXIST.equals(e.getError())
                || Error.MORE_PARTICIPANTS_THAN_LIMIT.equals(e.getError())
                || Error.TRAINING_CANNOT_HAVE_ACTIVE_STATUS.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getError().getMessage()));
        }
        return response;
    }
}
