package com.xaymaca.poc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Vincent Stoessel on August 01, 2016.
 *
 */
public class CamelRouteTests extends CamelSpringTestSupport {
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("direct:callMe")
                        .to("mock:received");

                from("restlet:http://localhost:8080/echo-post-body?restletMethods=post")
                        .removeHeaders("CamelHttp*")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                exchange.getOut().setBody(exchange.getIn().getBody());

                            }
                        })
                        .to("mock:postPoint")  ;

                from("restlet:http://localhost:8080/echo-query-string?restletMethods=get")
                        .removeHeaders("CamelHttp*",Exchange.HTTP_QUERY )
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {

                                exchange.getOut().setBody(exchange.getIn().getHeader(Exchange.HTTP_QUERY));

                            }
                        })
                        .to("mock:getPoint")  ;

            }
        };

    }


    @Test
    public void testSendAMessage() throws IOException, InterruptedException {
        MockEndpoint serviceResult = getMockEndpoint("mock:received");
        template.sendBody("direct:callMe", "Hello Moto");
        serviceResult.expectedMessageCount(1);
        String expectedResult = "Hello Moto";
        String resultString = (String) serviceResult.getExchanges().get(0).getIn().getBody();
        Assert.assertEquals(expectedResult, resultString);
        serviceResult.assertIsSatisfied();


    }

    @Test public void testRestletConnection() throws IOException {
        MockEndpoint serviceResult = getMockEndpoint("mock:getPoint");
        String response = IOUtils.toString((InputStream) template.requestBody("http://localhost:8080/echo-query-string?foo=bar","")) ;
        serviceResult.expectedMessageCount(1);
        String expected = "foo=bar";
        Assert.assertEquals(expected,response);

    }


    @Override
    protected AbstractApplicationContext createApplicationContext() {

        return new ClassPathXmlApplicationContext("vin-spring.xml");
    }
}
