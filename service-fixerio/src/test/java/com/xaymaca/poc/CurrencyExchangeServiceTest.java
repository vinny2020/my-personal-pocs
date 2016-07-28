package com.xaymaca.poc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaymaca.poc.model.CurrentExchangeRate;
import com.xaymaca.poc.model.TrendingRateInquiry;
import com.xaymaca.poc.model.TrendingResult;
import junit.framework.Assert;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xaymaca.poc.asyncUtils.BrexitToFixerIOAsyncRequests.fetchData;

/**
 * Created by Vincent Stoessel on July 07, 2016.
 *
 *
 */
public class CurrencyExchangeServiceTest extends CamelTestSupport {



    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:getLatest")
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .to("http://api.fixer.io/2016-07-04?base=GBP&symbols=USD")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("we got " + exchange.getIn().getBody());
                            }
                        })
                .to("mock:result");

                from("direct:getLatestDynamic")
                        .setExchangePattern(ExchangePattern.InOut)
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .to("http://api.fixer.io/2016-07-04")
                        .to("mock:dynamicResult");

                from("direct:get100")
                        .setExchangePattern(ExchangePattern.InOut)
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .to("http://api.fixer.io")
                        .to("mock:yearTrend");



            }
        };
    }




    @Test
    public void testGetLatestExchange() throws InterruptedException, IOException {
        MockEndpoint serviceResult = getMockEndpoint("mock:result");
         template.sendBody("direct:getLatest","");
        serviceResult.expectedMessageCount(1);
        String fourthOfJulyExpected = "{\"base\":\"GBP\",\"date\":\"2016-07-04\",\"rates\":{\"USD\":1.3275}}";
        String fourthOfJuly2016Query = IOUtils.toString((InputStream) serviceResult.getExchanges().get(0).getIn().getBody())  ;
        Assert.assertTrue(StringUtils.isNotEmpty(fourthOfJuly2016Query));
        Assert.assertEquals(fourthOfJulyExpected,fourthOfJuly2016Query);
        serviceResult.assertIsSatisfied();


    }


    @Test
    public void testGetLatestExchangeDynamically() throws InterruptedException, IOException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:getLatestDynamic");
        String fourthOfJuly2016Query1 = IOUtils.toString((InputStream) template.requestBodyAndHeader("direct:getLatestDynamic","",Exchange.HTTP_QUERY,"base=GBP&symbols=USD"));
        String fourthOfJulyExpected1 = "{\"base\":\"GBP\",\"date\":\"2016-07-04\",\"rates\":{\"USD\":1.3275}}";
        Assert.assertTrue(StringUtils.isNotEmpty(fourthOfJuly2016Query1));
        Assert.assertEquals(fourthOfJulyExpected1,fourthOfJuly2016Query1);
        mockEndpoint.assertIsSatisfied();


    }

    @Test
    public void testGetRateInJSON() throws InterruptedException, IOException {
        MockEndpoint jsonResult = getMockEndpoint("mock:getLatestDynamic");
        String fourthOfJuly2016Query = IOUtils.toString((InputStream) template.requestBodyAndHeader("direct:getLatestDynamic","",Exchange.HTTP_QUERY,"base=GBP&symbols=USD"));
        String fourthOfJulyExpected = "{\"base\":\"GBP\",\"date\":\"2016-07-04\",\"rates\":{\"USD\":1.3275}}";
        Assert.assertTrue(StringUtils.isNotEmpty(fourthOfJuly2016Query));
        Assert.assertEquals(fourthOfJulyExpected,fourthOfJuly2016Query);
        jsonResult.assertIsSatisfied();


    }


    @Test
    public void testShowJSON() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        TrendingRateInquiry trendingRateInquiry = new TrendingRateInquiry();
        trendingRateInquiry.setBaseCurrency("USD");
        trendingRateInquiry.setTargetCurrency("GBP");

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);


        trendingRateInquiry.setQuantity(10);
        trendingRateInquiry.setInterval("month");


      String json =   mapper.writeValueAsString( trendingRateInquiry);

        TrendingResult trendingResult = new TrendingResult();
        CurrentExchangeRate currentExchangeRate = new CurrentExchangeRate("2016-07-04",1.341f,  new LocalDate(new Date()));
        CurrentExchangeRate currentExchangeRate1 = new CurrentExchangeRate("2016-07-05", 1.313f, new LocalDate(new Date()));
        List<CurrentExchangeRate> currentExchangeRates = new ArrayList<>();
        currentExchangeRates.add(currentExchangeRate);
        currentExchangeRates.add(currentExchangeRate1);
        trendingResult.setInterval("month");
        trendingResult.setQuantity(1);
        trendingResult.setBaseCurrency("GBP");
        trendingResult.setTargetCurrency("USD");

        Assert.assertTrue(currentExchangeRates.size() == 15);
        trendingResult.setRates(currentExchangeRates);

        String trendResultJSON =  mapper.writeValueAsString(trendingResult);




        System.out.println("**************  The JSON is " + trendResultJSON  + " *********************");


    }




    @Test
    public void testCollectorByYear() throws IOException, InterruptedException {

        MockEndpoint jsonResult = getMockEndpoint("mock:yearTrend");
        List<CurrentExchangeRate> returnedRates= new ArrayList<>();
        List<HttpGet> httpGets = new ArrayList<HttpGet>();


        //first get me the right dates

        String rawIncomingJSON = "{\"quantity\":10,\"baseCurrency\":\"GBP\",\"targetCurrency\":\"USD\",\"interval\":\"now\"}";

        TrendingRateInquiry inquiry=   mapper.readValue(rawIncomingJSON, TrendingRateInquiry.class);


        String soughtInterval = inquiry.getInterval();
        int numberedInterval = getNumberedInterval(soughtInterval);
        int howManyUnits = inquiry.getQuantity();








        List<String> queryDates = new ArrayList<>();


        String baseCurrency = inquiry.getBaseCurrency().toUpperCase();
        String targetCurrency = inquiry.getTargetCurrency().toUpperCase();
        int interval = numberedInterval;
        DateTime today = new DateTime();

        for(int i =0; i<=interval; i++) {

            String date =  today.minusDays(i).toLocalDate().toString() ;


                queryDates.add(date);
//                String url = "http://api.fixer.io/" + date + "?base=" + baseCurrency +  "&symbols=" + targetCurrency ;
//
//                httpGets.add(new HttpGet(url));
            }



     //  Collections.reverse(queryDates);
    //    Assert.assertTrue(queryDates.size() == 11);



        for(String date : queryDates) {

                String url = "http://api.fixer.io/" + date + "?base=" + baseCurrency +  "&symbols=" + targetCurrency ;

                httpGets.add(new HttpGet(url));


        }

        returnedRates =  fetchData(httpGets, targetCurrency, howManyUnits);
        Collections.sort(returnedRates);

        TrendingResult trendingResult = new TrendingResult(1,baseCurrency,targetCurrency,"now",today.toLocalDate().toString(),returnedRates);
       // System.out.println(" ***   ****   returned this many results" + trendingResult.getRates().size());

        System.out.println(mapper.writeValueAsString(trendingResult));
        jsonResult.assertIsSatisfied();





    }

    private int getNumberedInterval(String soughtInterval) {
        int numberedInterval = 0;
        if(soughtInterval.equals("now")) {
            numberedInterval = 15;
        }
        else if(soughtInterval.equals("month")) {
            numberedInterval = 30;
        }
        else if(soughtInterval.equals("3month")) {
            numberedInterval = 90;
        }
        else if(soughtInterval.equals("year")) {
            numberedInterval = 365;
        }
        return numberedInterval;
    }













}
