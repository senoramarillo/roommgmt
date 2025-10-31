package com.spring.roommgmt.controller;


import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<HttpStatus> throwConflict() {
        return ResponseEntity.status(CONFLICT).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> throwBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpStatus> throwNotFound() {
        return ResponseEntity.notFound().build();
    }

}
