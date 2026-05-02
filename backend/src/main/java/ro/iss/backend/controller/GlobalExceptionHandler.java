package ro.iss.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.iss.backend.exception.FailedAuthentication;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FailedAuthentication.class)
    public ResponseEntity<String> handleException(FailedAuthentication e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
