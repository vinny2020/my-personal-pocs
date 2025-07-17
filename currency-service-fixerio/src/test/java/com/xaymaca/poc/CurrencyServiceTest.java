package com.xaymaca.poc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CurrencyService interface
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CurrencyService Tests")
public class CurrencyServiceTest {

    @Mock
    private CurrencyService mockCurrencyService;

    @Test
    @DisplayName("Should create mock CurrencyService")
    void shouldCreateMockCurrencyService() {
        // When & Then
        assertNotNull(mockCurrencyService, "Mock CurrencyService should be created");
        assertTrue(mockCurrencyService instanceof CurrencyService, 
            "Should be a CurrencyService instance");
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
    @DisplayName("Should be mockable")
    void shouldBeMockable() {
        // When & Then
        assertDoesNotThrow(() -> {
            CurrencyService service = mock(CurrencyService.class);
            assertNotNull(service);
        }, "Should be able to mock CurrencyService interface");
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