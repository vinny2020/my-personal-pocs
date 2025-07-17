package com.xaymaca.poc.routes;

import org.apache.camel.spring.boot.FatJarRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for XaymacaBootRouterWarInitializer
 */
@DisplayName("XaymacaBootRouterWarInitializer Tests")
public class XaymacaBootRouterWarInitializerTest {

    private XaymacaBootRouterWarInitializer initializer;

    @BeforeEach
    void setUp() {
        initializer = new XaymacaBootRouterWarInitializer();
    }

    @Test
    @DisplayName("Should create XaymacaBootRouterWarInitializer instance")
    void shouldCreateXaymacaBootRouterWarInitializerInstance() {
        // When & Then
        assertNotNull(initializer, "XaymacaBootRouterWarInitializer should be created");
        assertTrue(initializer instanceof XaymacaBootRouterWarInitializer, 
            "Should be a XaymacaBootRouterWarInitializer instance");
    }

    @Test
    @DisplayName("Should have correct class structure")
    void shouldHaveCorrectClassStructure() {
        // Given
        Class<?> clazz = XaymacaBootRouterWarInitializer.class;

        // When & Then
        assertEquals("com.xaymaca.poc.routes.XaymacaBootRouterWarInitializer", 
            clazz.getName(), "Class should have correct package name");
        assertTrue(clazz.getDeclaredMethods().length > 0, 
            "Class should have declared methods");
    }

    @Test
    @DisplayName("Should be instantiable")
    void shouldBeInstantiable() {
        // When & Then
        assertDoesNotThrow(() -> {
            XaymacaBootRouterWarInitializer instance = new XaymacaBootRouterWarInitializer();
            assertNotNull(instance);
        }, "Should be able to instantiate XaymacaBootRouterWarInitializer");
    }

    @Test
    @DisplayName("Should have public constructor")
    void shouldHavePublicConstructor() {
        // When & Then
        assertDoesNotThrow(() -> {
            XaymacaBootRouterWarInitializer.class.getDeclaredConstructor();
        }, "Should have accessible default constructor");
    }

    @Test
    @DisplayName("Should extend FatWarInitializer")
    void shouldExtendFatWarInitializer() {
        // When & Then
        assertTrue(XaymacaBootRouterWarInitializer.class.getSuperclass().getName()
            .contains("FatWarInitializer"), "Should extend FatWarInitializer");
    }

    @Test
    @DisplayName("Should have routerClass method")
    void shouldHaveRouterClassMethod() {
        // When & Then
        assertDoesNotThrow(() -> {
            XaymacaBootRouterWarInitializer.class.getDeclaredMethod("routerClass");
        }, "Should have routerClass method");
    }

    @Test
    @DisplayName("Should return XaymacaBootServices class from routerClass")
    void shouldReturnXaymacaBootServicesClassFromRouterClass() {
        // When
        Class<? extends FatJarRouter> routerClass = initializer.routerClass();

        // Then
        assertNotNull(routerClass, "Router class should not be null");
        assertEquals(XaymacaBootServices.class, routerClass, 
            "Should return XaymacaBootServices class");
    }

    @Test
    @DisplayName("Should create new XaymacaBootRouterWarInitializer instance")
    void shouldCreateNewXaymacaBootRouterWarInitializerInstance() {
        // When & Then
        XaymacaBootRouterWarInitializer instance = new XaymacaBootRouterWarInitializer();
        assertNotNull(instance, "XaymacaBootRouterWarInitializer should be created");
        assertTrue(instance instanceof XaymacaBootRouterWarInitializer, 
            "Should be a XaymacaBootRouterWarInitializer instance");
    }

    @Test
    @DisplayName("Should be accessible")
    void shouldBeAccessible() {
        // When & Then
        assertDoesNotThrow(() -> {
            Class.forName("com.xaymaca.poc.routes.XaymacaBootRouterWarInitializer");
        }, "Should be able to access XaymacaBootRouterWarInitializer class");
    }
} 