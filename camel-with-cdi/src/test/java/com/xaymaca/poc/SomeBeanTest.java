package com.xaymaca.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SomeBean
 */
@DisplayName("SomeBean Tests")
public class SomeBeanTest {

    private SomeBean someBean;

    @BeforeEach
    void setUp() {
        someBean = new SomeBean();
    }

    @Test
    @DisplayName("Should increment counter on each count() call")
    void shouldIncrementCounter() {
        // Given
        int firstCall = someBean.count();
        int secondCall = someBean.count();
        int thirdCall = someBean.count();

        // Then
        assertEquals(1, firstCall, "First call should return 1");
        assertEquals(2, secondCall, "Second call should return 2");
        assertEquals(3, thirdCall, "Third call should return 3");
    }

    @Test
    @DisplayName("Should maintain counter state across multiple calls")
    void shouldMaintainCounterState() {
        // When
        for (int i = 1; i <= 10; i++) {
            int result = someBean.count();
            assertEquals(i, result, "Call " + i + " should return " + i);
        }
    }

    @Test
    @DisplayName("Should start counter from 1")
    void shouldStartCounterFromOne() {
        // When
        int result = someBean.count();

        // Then
        assertEquals(1, result, "First count() call should return 1");
    }
} 