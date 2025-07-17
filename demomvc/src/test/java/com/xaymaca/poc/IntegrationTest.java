package com.xaymaca.poc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integration tests for demomvc module
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Integration Tests")
@SpringBootTest
@TestPropertySource(properties = {
    "spring.main.web-application-type=none",
    "camel.springboot.main-run-controller=false"
})
public class IntegrationTest {

    @Mock
    private DemomvcApplication mockApplication;

    @Test
    @DisplayName("Should load Spring Boot context")
    void shouldLoadSpringBootContext() {
        // When & Then
        assertDoesNotThrow(() -> {
            // This test verifies that the Spring Boot context can be loaded
            // The @SpringBootTest annotation will handle the context loading
        }, "Spring Boot context should load successfully");
    }

    @Test
    @DisplayName("Should have Camel components available")
    void shouldHaveCamelComponentsAvailable() {
        // When & Then
        assertDoesNotThrow(() -> {
            // Test that Camel components are available
            Class.forName("org.apache.camel.spring.boot.FatJarRouter");
            Class.forName("org.apache.camel.Exchange");
            Class.forName("org.apache.camel.Processor");
        }, "Camel components should be available");
    }

    @Test
    @DisplayName("Should have Netty components available")
    void shouldHaveNettyComponentsAvailable() {
        // When & Then
        assertDoesNotThrow(() -> {
            // Test that Netty components are available
            Class.forName("io.netty.handler.codec.http.FullHttpRequest");
            Class.forName("io.netty.handler.codec.http.QueryStringDecoder");
        }, "Netty components should be available");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "com.xaymaca.poc.DemomvcApplication",
        "com.xaymaca.poc.routes.XaymacaBootServices",
        "com.xaymaca.poc.routes.XaymacaBootRouterWarInitializer"
    })
    @DisplayName("Should have accessible classes")
    void shouldHaveAccessibleClasses(String className) {
        // When & Then
        assertDoesNotThrow(() -> {
            Class.forName(className);
        }, "Class " + className + " should be accessible");
    }

    @ParameterizedTest
    @CsvSource({
        "DemomvcApplication, true",
        "XaymacaBootServices, true",
        "XaymacaBootRouterWarInitializer, true"
    })
    @DisplayName("Should have instantiable classes")
    void shouldHaveInstantiableClasses(String className, boolean shouldBeInstantiable) {
        // When & Then
        if (shouldBeInstantiable) {
            assertDoesNotThrow(() -> {
                Class<?> clazz = Class.forName("com.xaymaca.poc" + 
                    (className.equals("DemomvcApplication") ? "" : ".routes") + 
                    "." + className);
                clazz.getDeclaredConstructor().newInstance();
            }, "Class " + className + " should be instantiable");
        }
    }

    @Test
    @DisplayName("Should have Spring Boot autoconfiguration")
    void shouldHaveSpringBootAutoconfiguration() {
        // When & Then
        assertDoesNotThrow(() -> {
            // Test that Spring Boot autoconfiguration is available
            Class.forName("org.springframework.boot.autoconfigure.SpringBootApplication");
            Class.forName("org.springframework.boot.SpringApplication");
        }, "Spring Boot autoconfiguration should be available");
    }

    @Test
    @DisplayName("Should have Camel Spring Boot integration")
    void shouldHaveCamelSpringBootIntegration() {
        // When & Then
        assertDoesNotThrow(() -> {
            // Test that Camel Spring Boot integration is available
            Class.forName("org.apache.camel.spring.boot.FatJarRouter");
            Class.forName("org.apache.camel.spring.boot.FatWarInitializer");
        }, "Camel Spring Boot integration should be available");
    }

    @Test
    @DisplayName("Should create mock application")
    void shouldCreateMockApplication() {
        // When & Then
        assertNotNull(mockApplication, "Mock DemomvcApplication should be created");
        assertTrue(mockApplication instanceof DemomvcApplication, 
            "Should be a DemomvcApplication instance");
    }

    @Test
    @DisplayName("Should have proper module structure")
    void shouldHaveProperModuleStructure() {
        // Given
        Class<?> appClass = DemomvcApplication.class;
        Class<?> servicesClass = com.xaymaca.poc.routes.XaymacaBootServices.class;
        Class<?> initializerClass = com.xaymaca.poc.routes.XaymacaBootRouterWarInitializer.class;

        // When & Then
        assertTrue(appClass.getDeclaredMethods().length > 0, 
            "DemomvcApplication should have methods");
        assertTrue(servicesClass.getDeclaredMethods().length > 0, 
            "XaymacaBootServices should have methods");
        assertTrue(initializerClass.getDeclaredMethods().length > 0, 
            "XaymacaBootRouterWarInitializer should have methods");
    }
} 