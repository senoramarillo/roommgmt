package com.spring.roommgmt.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Arrays.stream;

/**
 * Utility class for common object operations.
 * Provides helper methods for null checking and object validation.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class Objects {

    /**
     * Checks if any of the provided objects is not null.
     *
     * @param objects the objects to check
     * @return true if at least one object is not null, false otherwise
     */
    public static boolean anyNonNull(Object... objects) {
        return stream(objects).anyMatch(java.util.Objects::nonNull);
    }

}
