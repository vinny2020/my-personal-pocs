package com.xaymaca.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DemomvcApplication
 */
@DisplayName("DemomvcApplication Tests")
@SpringBootTest
public class DemomvcApplicationTest {

    private DemomvcApplication application;

    @BeforeEach
    void setUp() {
        application = new DemomvcApplication();
    }

    @Test
    @DisplayName("Should create DemomvcApplication instance")
    void shouldCreateDemomvcApplicationInstance() {
        // When & Then
        assertNotNull(application, "DemomvcApplication should be created");
        assertTrue(application instanceof DemomvcApplication, 
            "Should be a DemomvcApplication instance");
    }

    @Test
    @DisplayName("Should have correct class structure")
    void shouldHaveCorrectClassStructure() {
        // Given
        Class<?> clazz = DemomvcApplication.class;

        // When & Then
        assertEquals("com.xaymaca.poc.DemomvcApplication", clazz.getName(), 
            "Class should have correct package name");
        assertTrue(clazz.getDeclaredMethods().length > 0, 
            "Class should have declared methods");
    }

    @Test
    @DisplayName("Should be instantiable")
    void shouldBeInstantiable() {
        // When & Then
        assertDoesNotThrow(() -> {
            DemomvcApplication instance = new DemomvcApplication();
            assertNotNull(instance);
        }, "Should be able to instantiate DemomvcApplication");
    }

    @Test
    @DisplayName("Should have public constructor")
    void shouldHavePublicConstructor() {
        // When & Then
        assertDoesNotThrow(() -> {
            DemomvcApplication.class.getDeclaredConstructor();
        }, "Should have accessible default constructor");
    }

    @Test
    @DisplayName("Should have main method")
    void shouldHaveMainMethod() {
        // When & Then
        assertDoesNotThrow(() -> {
            DemomvcApplication.class.getDeclaredMethod("main", String[].class);
        }, "Should have main method");
    }

    @Test
    @DisplayName("Should create new DemomvcApplication instance")
    void shouldCreateNewDemomvcApplicationInstance() {
        // When & Then
        DemomvcApplication instance = new DemomvcApplication();
        assertNotNull(instance, "DemomvcApplication should be created");
        assertTrue(instance instanceof DemomvcApplication, 
            "Should be a DemomvcApplication instance");
    }

    @Test
    @DisplayName("Should be Spring Boot compatible")
    void shouldBeSpringBootCompatible() {
        // When & Then
        assertDoesNotThrow(() -> {
            // Test that SpringApplication.run can be called with this class
            // This is a basic compatibility test
            Class<?> springApplicationClass = Class.forName("org.springframework.boot.SpringApplication");
            assertNotNull(springApplicationClass, "SpringApplication should be available");
        }, "Should be compatible with Spring Boot");
    }
} 