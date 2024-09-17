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
        if (Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS.equals(e.getError())
                || Error.THERE_IS_NOT_REQUIRED_PARAMETER_CATEGORY.equals(e.getError())
                || Error.WRONG_DATE.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getError().getMessage()));
        }
        if (Error.TRAINING_ALREADY_EXIST.equals(e.getError())
                || Error.MORE_PARTICIPANTS_THAN_LIMIT.equals(e.getError())
                || Error.TRAINING_CANNOT_HAVE_ACTIVE_STATUS.equals(e.getError())
                || Error.TRAINING_IS_NOT_ACTIVE.equals(e.getError())
                || Error.CLIENT_IS_NOT_ACTIVE.equals(e.getError())
                || Error.CLIENT_DOES_NOT_HAVE_PREMIUM_OR_MASTER_TICKET.equals(e.getError())
                || Error.CLIENT_IS_ALREADY_SIGNED_UP_FOR_THIS_TRAINING.equals(e.getError())
                || Error.CLIENT_IS_NOT_ENROLLED_FOR_THIS_TRAINING.equals(e.getError())) {
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getError().getMessage()));
        }
        return response;
    }
}
