package com.xaymaca.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Parameterized tests for SomeBean
 */
@DisplayName("SomeBean Parameterized Tests")
public class SomeBeanParameterizedTest {

    private SomeBean someBean;

    @BeforeEach
    void setUp() {
        someBean = new SomeBean();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 20, 50, 100})
    @DisplayName("Should increment counter correctly for multiple calls")
    void shouldIncrementCounterCorrectly(int expectedValue) {
        // Given
        int result = 0;
        
        // When - call count() the expected number of times
        for (int i = 0; i < expectedValue; i++) {
            result = someBean.count();
        }

        // Then
        assertEquals(expectedValue, result, 
            "After " + expectedValue + " calls, counter should be " + expectedValue);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1",
        "2, 2", 
        "3, 3",
        "5, 5",
        "10, 10"
    })
    @DisplayName("Should return correct value for specific number of calls")
    void shouldReturnCorrectValueForSpecificCalls(int numberOfCalls, int expectedResult) {
        // Given
        int result = 0;
        
        // When
        for (int i = 0; i < numberOfCalls; i++) {
            result = someBean.count();
        }

        // Then
        assertEquals(expectedResult, result, 
            "After " + numberOfCalls + " calls, should return " + expectedResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 25, 50})
    @DisplayName("Should maintain sequential order for multiple calls")
    void shouldMaintainSequentialOrderForMultipleCalls(int numberOfCalls) {
        // Given
        int[] results = new int[numberOfCalls];
        
        // When
        for (int i = 0; i < numberOfCalls; i++) {
            results[i] = someBean.count();
        }

        // Then
        for (int i = 0; i < numberOfCalls; i++) {
            assertEquals(i + 1, results[i], 
                "Call " + (i + 1) + " should return " + (i + 1));
        }
    }
} 