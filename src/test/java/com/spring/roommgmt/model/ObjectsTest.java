package com.spring.roommgmt.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.spring.roommgmt.model.Objects.anyNonNull;
import static org.assertj.core.api.Assertions.assertThat;

class ObjectsTest {

    @Test
    @DisplayName("anyNonNull should return false if no object is null")
    void anyNonNull_shouldReturnFalse() {
        String something = null;

        assertThat(anyNonNull(null, null, something)).isFalse();
    }

    @Test
    @DisplayName("anyNonNull should return true if any object is not null")
    void anyNonNull_shouldReturnTrue_1() {
        String something = null;

        assertThat(anyNonNull(null, "hello room", something)).isTrue();
    }

    @Test
    @DisplayName("anyNonNull should return true if no object at all is null")
    void anyNonNull_shouldReturnTrue_2() {
        var something = "Peter!";

        assertThat(anyNonNull(1, "hello room", something)).isTrue();
    }

}
