package com.example.backend.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.backend.utils.Country.NORWAY;
import static com.example.backend.utils.Country.SWEDEN;
import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void givenWeekDay_whenIsWeekend_thenFalse(int number) {
        assertFalse(Utilities.isWeekend(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7})
    public void givenWeekend_whenIsWeekend_thenTrue(int number) {
        assertTrue(Utilities.isWeekend(number));
    }

    @Test
    public void givenSweden_whenGetStandardWorkingHours_then8H() {
        assertEquals(Utilities.getStandardWorkingHours(SWEDEN.country), 8.0);
    }

    @Test
    public void givenNorway_whenGetStandardWorkingHours_then7And5H() {
        assertEquals(Utilities.getStandardWorkingHours(NORWAY.country), 7.5);
    }

    @Test
    public void givenSweden_whenGetTotalHours_then2024H() {
        assertEquals(Utilities.getTotalHours(SWEDEN.country), 2024);
    }

    @Test
    public void givenNorway_whenGetTotalHours_then1905H() {
        assertEquals(Utilities.getTotalHours(NORWAY.country), 1905);
    }

    @Test
    public void givenSweden_whenGetTotalDaysByCountry_then253days() {
        assertEquals(Utilities.getTotalDaysByCountry(SWEDEN.country), 253);
    }

    @Test
    public void givenNorway_whenGetTotalDaysByCountry_then254days() {
        assertEquals(Utilities.getTotalDaysByCountry(NORWAY.country), 254);
    }

    @ParameterizedTest
    @CsvSource({"224D, 225", "0D, 253", "2024D, 0", "125.5, 237.3"})
    void givenSweden_whenCountRemainingDays_thenExpectedDays(double input, double expected) {
        double actualValue = Utilities.countRemainingDays(input, SWEDEN.country);
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @CsvSource({"224D, 224.1", "0D, 254", "1905D, 0", "125.5, 237.3"})
    void givenNorway_whenCountRemainingDays_thenExpectedDays(double input, double expected) {
        double actualValue = Utilities.countRemainingDays(input, NORWAY.country);
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @CsvSource({"1.35486, 1.4", "125.789, 125.8", "3.999, 4.0", "125.5, 125.5"})
    void givenDoubleWithMultipleDecimalPoints_whenRoundToOneDecimalPoint_thenExpectedDouble(double input, double expected) {
        double actualValue = Utilities.roundToOneDecimalPoint(input);
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @CsvSource({"1.35486, 1.35", "125.789, 125.79", "3.999, 4.0", "125.565, 125.57"})
    void givenDoubleWithMultipleDecimalPoints_whenRoundToTwoDecimalPoints_thenExpectedDouble(double input, double expected) {
        double actualValue = Utilities.roundToTwoDecimalPoints(input);
        assertEquals(expected, actualValue);
    }
}