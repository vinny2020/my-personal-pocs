package com.xaymaca.poc.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Vincent Stoessel on August 01, 2016.
 */
@SpringBootApplication
public class XaymacaServices extends FatJarRouter {

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
                .removeHeaders("CamelHttp*",Exchange.HTTP_QUERY )
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getHeader(Exchange.HTTP_QUERY));

                    }
                })

        ;

    }
}
