package com.spring.roommgmt.service.exception;

/**
 * Exception thrown when attempting to create a duplicate resource.
 * Typically thrown when trying to insert an entity with a unique constraint violation.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
public class DuplicateKeyException extends RuntimeException {
}
