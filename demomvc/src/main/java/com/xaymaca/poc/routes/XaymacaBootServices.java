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
                        String queryString = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);
                        String response = "Hello! We found these parameters in the query string: " + (queryString != null ? queryString : "none");
                        exchange.getIn().setBody(response);
                    }
                })

        ;

    }
}
