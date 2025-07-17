package com.xaymaca.poc;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * CDI Integration tests
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CDI Integration Tests")
public class CDIIntegrationTest {

    @Mock
    private SomeBean mockSomeBean;

    private CamelContext camelContext;
    private SimpleRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new SimpleRegistry();
        camelContext = new DefaultCamelContext(registry);
        // Register the mock bean for testing
        registry.bind("counterBean", mockSomeBean);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (camelContext != null) {
            camelContext.stop();
        }
    }

    @Test
    @DisplayName("Should register mock bean in registry and verify interaction")
    void shouldRegisterMockBeanAndVerifyInteraction() {
        // When
        Object registeredBean = registry.lookupByName("counterBean");
        assertNotNull(registeredBean, "Bean should be registered in registry");
        assertSame(mockSomeBean, registeredBean, "Should be the same mock bean");

        // When
        mockSomeBean.count();
        mockSomeBean.count();
        // Then
        verify(mockSomeBean, times(2)).count();
    }

    @Test
    @DisplayName("Should create SomeBean with CDI annotations")
    void shouldCreateSomeBeanWithCDIAnnotations() {
        // Given
        SomeBean someBean = new SomeBean();

        // When & Then
        assertNotNull(someBean, "SomeBean should be created");
        assertTrue(SomeBean.class.isAnnotationPresent(jakarta.inject.Singleton.class),
            "SomeBean should have @Singleton annotation");
        assertTrue(SomeBean.class.isAnnotationPresent(jakarta.inject.Named.class),
            "SomeBean should have @Named annotation");
    }

    @Test
    @DisplayName("Should have correct bean name")
    void shouldHaveCorrectBeanName() {
        // Given
        SomeBean someBean = new SomeBean();
        jakarta.inject.Named namedAnnotation = SomeBean.class.getAnnotation(jakarta.inject.Named.class);

        // When
        String beanName = namedAnnotation.value();

        // Then
        assertEquals("counterBean", beanName, "Bean should be named 'counterBean'");
    }

    @Test
    @DisplayName("Should increment counter when called")
    void shouldIncrementCounterWhenCalled() {
        // Given
        SomeBean someBean = new SomeBean();

        // When
        int firstCall = someBean.count();
        int secondCall = someBean.count();
        int thirdCall = someBean.count();

        // Then
        assertEquals(1, firstCall, "First call should return 1");
        assertEquals(2, secondCall, "Second call should return 2");
        assertEquals(3, thirdCall, "Third call should return 3");
    }
} 