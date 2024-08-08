package com.example.backend.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void shouldReturnFalseWhenWeekDay(int number) {
        assertFalse(Utilities.isWeekend(number));
    }
    @ParameterizedTest
    @ValueSource(ints = {6,7})
    public void shouldReturnTrueWhenWeekend(int number) {
        assertTrue(Utilities.isWeekend(number));
    }
}