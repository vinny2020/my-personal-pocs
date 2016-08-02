package com.xaymaca.poc.routes;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.component.netty4.http.NettyHttpMessage;
import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Vincent Stoessel on August 01, 2016.
 *
 */
@SpringBootApplication
public class XaymacaBootServices extends FatJarRouter {

    @Override
    public void configure() throws Exception {

        from("netty4-http:http://0.0.0.0:9090/echo-post-body")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getOut().setBody(exchange.getIn().getBody());

                    }
                })

                .from("netty4-http:http://0.0.0.0:9090/echo-query-string?mapHeaders=true")
                .setExchangePattern(ExchangePattern.InOut)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        FullHttpRequest nettyRequest = ((NettyHttpMessage) exchange.getIn()).getHttpRequest();
                        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(nettyRequest.getUri());
                        String responseMapString = queryStringDecoder.parameters().toString();
                        String response = responseMapString.replaceAll("[\\[\\]{}]", "");
                        exchange.getIn().setBody("Hello! We found these parameters in the query string: " + response);

                    }
                })

        ;

    }
}
