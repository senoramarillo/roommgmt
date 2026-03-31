package com.spring.roommgmt.controller;

import com.spring.roommgmt.controller.dto.ApiErrorResponse;
import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceConflictException;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Global Exception Handler Controller.
 * Handles exceptions thrown across the application and maps them to appropriate HTTP responses.
 * Uses @ControllerAdvice to provide centralized exception handling for all controllers.
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiErrorResponse> throwConflict(DuplicateKeyException exception, HttpServletRequest request) {
        return buildErrorResponse(CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiErrorResponse> throwResourceConflict(ResourceConflictException exception, HttpServletRequest request) {
        return buildErrorResponse(CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> throwBadRequest(IllegalArgumentException exception, HttpServletRequest request) {
        return buildErrorResponse(BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> throwNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return buildErrorResponse(NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> throwValidationError(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() == null ? "Invalid value" : fieldError.getDefaultMessage(),
                        (left, right) -> left
                ));
        return buildErrorResponse(BAD_REQUEST, "Request validation failed", request, validationErrors);
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, String> validationErrors
    ) {
        return ResponseEntity.status(status).body(new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message == null || message.isBlank() ? status.getReasonPhrase() : message,
                request.getRequestURI(),
                validationErrors
        ));
    }
}
