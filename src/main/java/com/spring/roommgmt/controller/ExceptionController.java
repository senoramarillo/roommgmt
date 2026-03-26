package com.spring.roommgmt.controller;


import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Global Exception Handler Controller.
 * Handles exceptions thrown across the application and maps them to appropriate HTTP responses.
 * Uses @ControllerAdvice to provide centralized exception handling for all controllers.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Handles DuplicateKeyException.
     * Returns HTTP 409 Conflict status when attempting to create a duplicate resource.
     *
     * @return ResponseEntity with HTTP Status 409 Conflict
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<HttpStatus> throwConflict() {
        return ResponseEntity.status(CONFLICT).build();
    }

    /**
     * Handles IllegalArgumentException.
     * Returns HTTP 400 Bad Request status when invalid arguments are provided.
     *
     * @return ResponseEntity with HTTP Status 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> throwBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    /**
     * Handles ResourceNotFoundException.
     * Returns HTTP 404 Not Found status when a requested resource cannot be found.
     *
     * @return ResponseEntity with HTTP Status 404 Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpStatus> throwNotFound() {
        return ResponseEntity.notFound().build();
    }

}
