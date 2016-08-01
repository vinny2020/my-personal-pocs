package com.xaymaca.poc;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

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

            }
        };
    }


    @Test
    public void testSendAMessage() throws IOException, InterruptedException {
        MockEndpoint serviceResult = getMockEndpoint("mock:received");
        template.sendBody("direct:callMe","Hello Moto");
        serviceResult.expectedMessageCount(1);
        String expectedResult = "Hello Moto" ;
        String  resultString = (String) serviceResult.getExchanges().get(0).getIn().getBody();
        Assert.assertEquals(expectedResult, resultString);
        serviceResult.assertIsSatisfied();





    }


    @Override
    protected AbstractApplicationContext createApplicationContext() {

        return new ClassPathXmlApplicationContext("vin-spring.xml");
    }
}
