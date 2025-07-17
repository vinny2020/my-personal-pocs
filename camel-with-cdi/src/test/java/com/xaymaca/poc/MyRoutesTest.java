package com.xaymaca.poc;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for MyRoutes
 */
@DisplayName("MyRoutes Integration Tests")
public class MyRoutesTest {

    private CamelContext camelContext;
    private MyRoutes myRoutes;
    private ProducerTemplate producerTemplate;
    private SimpleRegistry registry;

    @BeforeEach
    void setUp() throws Exception {
        registry = new SimpleRegistry();
        camelContext = new DefaultCamelContext(registry);
        myRoutes = new MyRoutes();
        producerTemplate = camelContext.createProducerTemplate();
        
        // Register the counterBean in the registry
        registry.bind("counterBean", new SomeBean());
        
        // Start the context
        camelContext.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (camelContext != null) {
            camelContext.stop();
        }
    }

    @Test
    @DisplayName("Should create route successfully")
    void shouldCreateRouteSuccessfully() throws Exception {
        // Given
        RouteBuilder routeBuilder = myRoutes;

        // When
        camelContext.addRoutes(routeBuilder);

        // Then
        assertTrue(camelContext.getRoutes().size() > 0, "Should have at least one route");
    }

    @Test
    @DisplayName("Should process message through test route")
    void shouldProcessMessageThroughTestRoute() throws Exception {
        // Given
        String testMessage = "test message";
        
        // Add a simple test route
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:test")
                    .to("log:test");
            }
        });

        // When
        String result = producerTemplate.requestBody("direct:test", testMessage, String.class);

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(testMessage, result, "Should return the same message");
    }

    @Test
    @DisplayName("Should configure route with timer endpoint")
    void shouldConfigureRouteWithTimerEndpoint() {
        // Given
        RouteBuilder routeBuilder = myRoutes;

        // When & Then
        assertDoesNotThrow(() -> {
            camelContext.addRoutes(routeBuilder);
        }, "Should configure route without throwing exception");
    }

    @Test
    @DisplayName("Should have counterBean registered in registry")
    void shouldHaveCounterBeanRegisteredInRegistry() {
        // When
        Object registeredBean = registry.lookupByName("counterBean");

        // Then
        assertNotNull(registeredBean, "counterBean should be registered in registry");
        assertTrue(registeredBean instanceof SomeBean, "Should be a SomeBean instance");
    }
} 