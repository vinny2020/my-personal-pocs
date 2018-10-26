package com.xaymaca.poc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaymaca.poc.model.CurrentExchangeRate;
import com.xaymaca.poc.model.TrendingRateInquiry;
import com.xaymaca.poc.model.TrendingResult;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xaymaca.poc.asyncUtils.BrexitToFixerIOAsyncRequests.fetchData;

/**
 * Created by Vincent Stoessel on July 07, 2016.
 * TODO refactor to use java.time , build FE
 */
public class CurrencyExchangeServiceTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeServiceTest.class);


    final static ObjectMapper mapper = new ObjectMapper();
    CamelContext camelContext;


    @Override
    protected CamelContext createCamelContext() throws Exception {

        JndiRegistry registry = new JndiRegistry();

        CamelContext camelContext = new DefaultCamelContext(registry);
        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("file:${env:EXT_LOCATION}/external.properties");
        camelContext.addComponent("properties", pc);
        this.camelContext = camelContext;

        return camelContext;
    }


    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:getLatest")
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .to("http://data.fixer.io/2016-07-04?access_key={{fixer.io.key}}&base=EUR&symbols=USD")
                        .to("mock:result");

                from("direct:getLatestDynamic")
                        .setExchangePattern(ExchangePattern.InOut)
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .setHeader(Exchange.HTTP_QUERY, simple("access_key={{fixer.io.key}}&base=EUR&symbols=USD"))
                        .to("http://data.fixer.io/2016-07-04?access_key={{fixer.io.key}}")
                        .to("mock:dynamicResult");


                from("direct:get100")
                        .setExchangePattern(ExchangePattern.InOut)
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .to("http://data.fixer.io?access_key={{fixer.io.key}}")
                        .to("mock:yearTrend");
            }
        };


    }


    @Test
    public void testGetLatestExchange() throws InterruptedException, IOException {
        MockEndpoint serviceResult = getMockEndpoint("mock:result");
        template.sendBody("direct:getLatest", "");
        serviceResult.expectedMessageCount(1);
        String fourthOfJulyExpected = "{\"success\":true,\"timestamp\":1467676799,\"historical\":true,\"base\":\"EUR\",\"date\":\"2016-07-04\",\"rates\":{\"USD\":1.114951}}";
        String fourthOfJuly2016Query = IOUtils.toString((InputStream) serviceResult.getExchanges().get(0).getIn().getBody());
        Assert.assertTrue(StringUtils.isNotEmpty(fourthOfJuly2016Query));
        Assert.assertEquals(fourthOfJulyExpected, fourthOfJuly2016Query);
        serviceResult.assertIsSatisfied();

    }


    @Test
    public void testGetLatestExchangeDynamicallyInJson() throws InterruptedException, IOException {
        MockEndpoint jsonResult = getMockEndpoint("mock:getLatestDynamic");
        String fourthOfJuly2016Query = IOUtils.toString((InputStream) template.requestBody("direct:getLatestDynamic", ""));
        String fourthOfJuly2016Expected = "{\"success\":true,\"timestamp\":1467676799,\"historical\":true,\"base\":\"EUR\",\"date\":\"2016-07-04\",\"rates\":{\"USD\":1.114951}}";
        Assert.assertTrue(StringUtils.isNotEmpty(fourthOfJuly2016Query));
        Assert.assertEquals(fourthOfJuly2016Expected, fourthOfJuly2016Query);
        jsonResult.assertIsSatisfied();
    }


    @Test
    public void testShowJSON() throws JsonProcessingException {

        TrendingRateInquiry trendingRateInquiry = new TrendingRateInquiry();
        trendingRateInquiry.setBaseCurrency("USD");
        trendingRateInquiry.setTargetCurrency("EUR");

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);

        trendingRateInquiry.setQuantity(10);
        trendingRateInquiry.setInterval("month");

        TrendingResult trendingResult = new TrendingResult();
        CurrentExchangeRate currentExchangeRate = new CurrentExchangeRate("2016-07-04", 1.341f, new LocalDate(new Date()));
        CurrentExchangeRate currentExchangeRate1 = new CurrentExchangeRate("2016-07-05", 1.313f, new LocalDate(new Date()));
        List<CurrentExchangeRate> currentExchangeRates = new ArrayList<>();
        currentExchangeRates.add(currentExchangeRate);
        currentExchangeRates.add(currentExchangeRate1);
        trendingResult.setInterval("month");
        trendingResult.setQuantity(1);
        trendingResult.setBaseCurrency("EUR");
        trendingResult.setTargetCurrency("USD");

        Assert.assertTrue(currentExchangeRates.size() == 2);
        trendingResult.setRates(currentExchangeRates);

        String trendResultJSON = mapper.writeValueAsString(trendingResult);
        LOGGER.debug("**************  The JSON is " + trendResultJSON + " *********************");

    }


    @Test
    public void testCollectorByYear() throws Exception {

        MockEndpoint jsonResult = getMockEndpoint("mock:yearTrend");
        List<CurrentExchangeRate> returnedRates;
        List<HttpGet> httpGets = new ArrayList<HttpGet>();

        //first get me the right dates

        String rawIncomingJSON = "{\"quantity\":10,\"baseCurrency\":\"EUR\",\"targetCurrency\":\"USD\",\"interval\":\"now\"}";

        TrendingRateInquiry inquiry = mapper.readValue(rawIncomingJSON, TrendingRateInquiry.class);


        String soughtInterval = inquiry.getInterval();
        int numberedInterval = getNumberedInterval(soughtInterval);
        int howManyUnits = inquiry.getQuantity();


        List<String> queryDates = new ArrayList<>();


        String baseCurrency = inquiry.getBaseCurrency().toUpperCase();
        String targetCurrency = inquiry.getTargetCurrency().toUpperCase();
        int interval = numberedInterval;
        DateTime today = new DateTime();

        for (int i = 0; i <= interval; i++) {

            String date = today.minusDays(i).toLocalDate().toString();


            queryDates.add(date);

            String accessKey = camelContext.resolvePropertyPlaceholders("{{fixer.io.key}}");

            for (String dateString : queryDates) {
                String url = "http://data.fixer.io/" + dateString + "?access_key=" + accessKey + "&base=" + baseCurrency + "&symbols=" + targetCurrency;
                httpGets.add(new HttpGet(url));

            }

            returnedRates = fetchData(httpGets, targetCurrency, howManyUnits);
            TrendingResult trendingResult = new TrendingResult(1, baseCurrency, targetCurrency, "now", today.toLocalDate().toString(), returnedRates);
            // LOGGER.debug(" ***   ****   returned this many results" + trendingResult.getRates().size());

            LOGGER.info(mapper.writeValueAsString(trendingResult));
            jsonResult.assertIsSatisfied();


        }
    }


    private int getNumberedInterval(String soughtInterval) {
        int numberedInterval = 0;
        if (soughtInterval.equals("now")) {
            numberedInterval = 15;
        } else if (soughtInterval.equals("month")) {
            numberedInterval = 30;
        } else if (soughtInterval.equals("3month")) {
            numberedInterval = 90;
        } else if (soughtInterval.equals("year")) {
            numberedInterval = 365;
        }
        return numberedInterval;
    }


}
