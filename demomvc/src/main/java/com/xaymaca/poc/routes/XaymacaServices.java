package com.xaymaca.poc.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Vincent Stoessel on August 01, 2016.
 */
@Component
public class XaymacaServices extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("restlet:/echo-post-body?restletMethods=post")
                .removeHeaders("CamelHttp*")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getBody());

                    }
                })

        .from("restlet:/echo-query-string?restletMethods=get")
                .removeHeaders("CamelHttp*")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getHeader(Exchange.HTTP_QUERY));

                    }
                })

        ;

    }
}
