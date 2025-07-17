package com.xaymaca.poc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for model classes
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Model Classes Tests")
public class ModelClassesTest {

    @Mock
    private FixerResult mockFixerResult;

    @BeforeEach
    void setUp() {
        // Setup any common test data
    }

    @Test
    @DisplayName("Should create FixerResult with valid data")
    void shouldCreateFixerResultWithValidData() {
        // Given
        String date = "2023-01-01";
        Map<String, Float> rates = new HashMap<>();
        rates.put("USD", 1.0f);
        rates.put("EUR", 0.85f);

        // When
        FixerResult result = new FixerResult();
        result.setDate(date);
        result.setRates(rates);

        // Then
        assertNotNull(result, "FixerResult should be created");
        assertEquals(date, result.getDate(), "Date should be set correctly");
        assertEquals(rates, result.getRates(), "Rates should be set correctly");
    }

    @Test
    @DisplayName("Should create CurrentExchangeRate with valid data")
    void shouldCreateCurrentExchangeRateWithValidData() {
        // Given
        String date = "2023-01-01";
        Float value = 1.25f;
        LocalDate localDate = LocalDate.parse("2023-01-01");

        // When
        CurrentExchangeRate exchangeRate = new CurrentExchangeRate(date, value, localDate);

        // Then
        assertNotNull(exchangeRate, "CurrentExchangeRate should be created");
        assertEquals(date, exchangeRate.getDate(), "Date should be set correctly");
        assertEquals(value, exchangeRate.getValue(), "Value should be set correctly");
        assertEquals(localDate, exchangeRate.getLocalDate(), "LocalDate should be set correctly");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023-01-01", "2023-12-31", "2024-06-15"})
    @DisplayName("Should create FixerResult with different dates")
    void shouldCreateFixerResultWithDifferentDates(String date) {
        // Given
        Map<String, Float> rates = new HashMap<>();
        rates.put("USD", 1.0f);

        // When
        FixerResult result = new FixerResult();
        result.setDate(date);
        result.setRates(rates);

        // Then
        assertEquals(date, result.getDate(), "Date should be set correctly");
        assertEquals(rates, result.getRates(), "Rates should be set correctly");
    }

    @ParameterizedTest
    @CsvSource({
        "USD, 1.0",
        "EUR, 0.85",
        "GBP, 0.75",
        "JPY, 110.0"
    })
    @DisplayName("Should create FixerResult with different currencies")
    void shouldCreateFixerResultWithDifferentCurrencies(String currency, Float rate) {
        // Given
        Map<String, Float> rates = new HashMap<>();
        rates.put(currency, rate);

        // When
        FixerResult result = new FixerResult();
        result.setRates(rates);

        // Then
        assertEquals(rate, result.getRates().get(currency), 
            "Rate for " + currency + " should be set correctly");
    }

    @Test
    @DisplayName("Should handle null values in FixerResult")
    void shouldHandleNullValuesInFixerResult() {
        // When
        FixerResult result = new FixerResult();

        // Then
        assertNull(result.getDate(), "Date should be null initially");
        assertNull(result.getRates(), "Rates should be null initially");
    }

    @Test
    @DisplayName("Should create mock FixerResult")
    void shouldCreateMockFixerResult() {
        // When & Then
        assertNotNull(mockFixerResult, "Mock FixerResult should be created");
        assertTrue(mockFixerResult instanceof FixerResult, 
            "Should be a FixerResult instance");
    }

    @Test
    @DisplayName("Should verify model class structure")
    void shouldVerifyModelClassStructure() {
        // Given
        Class<?> fixerResultClass = FixerResult.class;
        Class<?> currentExchangeRateClass = CurrentExchangeRate.class;

        // When & Then
        assertTrue(fixerResultClass.getDeclaredFields().length > 0, 
            "FixerResult should have declared fields");
        assertTrue(currentExchangeRateClass.getDeclaredFields().length > 0, 
            "CurrentExchangeRate should have declared fields");
    }

    @Test
    @DisplayName("Should have accessible constructors")
    void shouldHaveAccessibleConstructors() {
        // When & Then
        assertDoesNotThrow(() -> {
            FixerResult.class.getDeclaredConstructor();
            CurrentExchangeRate.class.getDeclaredConstructor(String.class, Float.class, LocalDate.class);
        }, "Should have accessible constructors");
    }
} 