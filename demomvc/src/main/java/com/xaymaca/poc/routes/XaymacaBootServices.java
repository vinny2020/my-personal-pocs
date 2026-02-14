package com.xaymaca.poc.routes;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Vincent Stoessel on August 01, 2016.
 *
 */
@SpringBootApplication
public class XaymacaBootServices extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("netty-http:http://0.0.0.0:9090/echo-post-body")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getBody());

                    }
                })

                .from("netty-http:http://0.0.0.0:9090/echo-query-string?mapHeaders=true")
                .setExchangePattern(ExchangePattern.InOut)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        StringBuilder response = new StringBuilder("Hello! We found these parameters in the query string: ");
                        // Query parameters are mapped to headers when mapHeaders=true
                        // They are usually prefixed with CamelHttpQuery.
                        // Or simply CamelNettyHttp.query.
                        // Iterate through headers and find query parameters.
                        exchange.getIn().getHeaders().forEach((key, value) -> {
                            if (key.startsWith("CamelHttpQuery.")) {
                                response.append(key.substring("CamelHttpQuery.".length())).append("=").append(value).append(", ");
                            } else if (key.startsWith("CamelNettyHttp.query.")) { // Fallback for Netty specific header name
                                response.append(key.substring("CamelNettyHttp.query.".length())).append("=").append(value).append(", ");
                            }
                        });
                        // Remove trailing ", " if any
                        if (response.length() > "Hello! We found these parameters in the query string: ".length()) {
                            response.setLength(response.length() - 2);
                        }
                        exchange.getIn().setBody(response.toString());
                    }
                })

        ;

    }
}
