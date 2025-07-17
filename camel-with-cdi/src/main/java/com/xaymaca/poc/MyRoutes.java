package com.xaymaca.poc;

import jakarta.inject.Inject;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Configures all our Camel routes, components, endpoints and beans
 */
public class MyRoutes extends RouteBuilder {

    @Inject
    Endpoint inputEndpoint;

    @Inject
    Endpoint resultEndpoint;

    @Override
    public void configure() {
        // you can configure the route rule with Java DSL here
        from("timer:foo?period=5000")
            .to("bean:counterBean")
            .to("log:output");
    }
}
