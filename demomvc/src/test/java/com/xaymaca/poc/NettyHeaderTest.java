package com.xaymaca.poc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Vincent Stoessel on August 03, 2016.
 */
public class NettyHeaderTest extends CamelTestSupport {


    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("netty4-http:http://localhost:8080/show-me-the-header")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {

                                String queryString = (String) exchange.getIn().getHeader(Exchange.HTTP_QUERY);
                                exchange.getIn().setBody("We have the query "  + queryString );
                            }
                        })
                        .to("mock:headerMessage")  ;

            }
        };
    }


    @Test public void testShowMeTheHeader() throws IOException {
            MockEndpoint serviceResult = getMockEndpoint("mock:headerMessage");
            template.requestBody("netty4-http:http://localhost:8080/show-me-the-header?foo=bar","") ;
            String bodyContent  = (String) serviceResult.getExchanges().get(0).getIn().getBody();
            String expected = "We have the query foo=bar";
            Assert.assertEquals(expected, bodyContent);
            serviceResult.expectedMessageCount(1);

        }
    }






