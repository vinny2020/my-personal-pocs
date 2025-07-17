package com.xaymaca.poc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CurrencyService interface
 */
@DisplayName("CurrencyService Tests")
public class CurrencyServiceTest {

    @Test
    @DisplayName("Should create CurrencyService interface")
    void shouldCreateCurrencyServiceInterface() {
        // When & Then
        assertTrue(CurrencyService.class.isInterface(), "CurrencyService should be an interface");
    }

    @Test
    @DisplayName("Should have correct interface structure")
    void shouldHaveCorrectInterfaceStructure() {
        // Given
        Class<?> clazz = CurrencyService.class;

        // When & Then
        assertTrue(clazz.isInterface(), "CurrencyService should be an interface");
        assertEquals("com.xaymaca.poc.CurrencyService", clazz.getName(), 
            "Interface should have correct package name");
    }

    @Test
    @DisplayName("Should be accessible interface")
    void shouldBeAccessibleInterface() {
        // When & Then
        assertDoesNotThrow(() -> {
            Class.forName("com.xaymaca.poc.CurrencyService");
        }, "Should be able to access CurrencyService interface");
    }

    @Test
    @DisplayName("Should have accessible interface")
    void shouldHaveAccessibleInterface() {
        // When & Then
        assertDoesNotThrow(() -> {
            Class.forName("com.xaymaca.poc.CurrencyService");
        }, "Should be able to access CurrencyService interface");
    }
} 