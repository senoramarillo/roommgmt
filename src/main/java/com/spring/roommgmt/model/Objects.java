package com.spring.roommgmt.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Arrays.stream;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class Objects {

    public static boolean anyNonNull(Object... objects) {
        return stream(objects).anyMatch(java.util.Objects::nonNull);
    }

}
