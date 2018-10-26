package com.xaymaca.poc.asyncUtils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.xaymaca.poc.model.CurrentExchangeRate;
import com.xaymaca.poc.model.FixerResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Vincent Stoessel on July 12, 2016.
 */
public class BrexitToFixerIOAsyncRequests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrexitToFixerIOAsyncRequests.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectReader fixerReader = objectMapper.reader(FixerResult.class);

    public static List<CurrentExchangeRate> fetchData(List<HttpGet> httpGets, final String targetCurrency, final Integer howMany) throws InterruptedException, IOException {

        final CopyOnWriteArrayList<CurrentExchangeRate> currentExchangeRates = new CopyOnWriteArrayList<>();
        final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000).build();

        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        cm.setMaxTotal(100);

        HttpGet[] requests = httpGets.toArray(new HttpGet[0]);


        try (CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(400)
                .setMaxConnTotal(400)
                .build()) {
            httpAsyncClient.start();
            final CountDownLatch latch = new CountDownLatch(httpGets.size());

            for (final HttpGet request : requests) {
                httpAsyncClient.execute(request, new FutureCallback<HttpResponse>() {

                    @Override
                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        try {
                            FixerResult resultObj = fixerReader.readValue(response.getEntity().getContent());
                            if (null == resultObj.getDate()) {
                                LOGGER.info("Our bad result were : " + resultObj.toString());
                            }
                            currentExchangeRates.add(new CurrentExchangeRate(resultObj.getDate(), resultObj.getRates().get(targetCurrency) * howMany, formatter.parseLocalDate(resultObj.getDate())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(final Exception ex) {
                        latch.countDown();
                        LOGGER.info(request.getRequestLine() + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        latch.countDown();
                        LOGGER.info(request.getRequestLine() + " cancelled");
                    }
                });
            }
            latch.await();
            LOGGER.info("Async Client Complete");
        }

        return currentExchangeRates;
    }
}


