package com.spring.roommgmt.service.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Typically thrown when querying for an entity that does not exist in the database.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with no detail message.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
