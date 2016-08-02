package com.xaymaca.poc;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.netty4.http.NettyHttpMessage;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;

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

                from("netty4-http:http://localhost:8080/echo-post-body")
                        .removeHeaders("CamelHttp*")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                exchange.getOut().setBody(exchange.getIn().getBody());

                            }
                        })
                        .to("mock:postPoint")  ;

                from("netty4-http:http://localhost:8080/echo-query-string")
                        .removeHeaders("CamelHttp*",Exchange.HTTP_QUERY )
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {

                                FullHttpRequest nettyRequest = ((NettyHttpMessage) exchange.getIn()).getHttpRequest();
                                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(nettyRequest.getUri());
                                Map params = queryStringDecoder.parameters();


                                exchange.getIn().setBody(params.toString());

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
        template.sendBody("netty4-http:http://localhost:8080/echo-query-string?foo=bar","") ;
        String responseMapString = (String) serviceResult.getExchanges().get(0).getIn().getBody();
        String response = responseMapString.replaceAll("[\\[\\]{}]","");
        serviceResult.expectedMessageCount(1);
        String expected = "foo=bar";
        Assert.assertEquals(expected,response);

    }


    @Override
    protected AbstractApplicationContext createApplicationContext() {

        return new ClassPathXmlApplicationContext("vin-spring.xml");
    }
}
