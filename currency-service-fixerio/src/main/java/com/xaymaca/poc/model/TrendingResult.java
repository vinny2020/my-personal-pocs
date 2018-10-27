package com.xaymaca.poc.model;

import java.util.List;

/**
 * Created by Vincent Stoessel on July 08, 2016.
 */
public class TrendingResult {


    Integer quantity;
    String baseCurrency;
    String targetCurrency;
    String interval ;  // now, month, 3month, year
    String currentDate;

    List<CurrentExchangeRate> rates;


    public TrendingResult() {
    }

    public TrendingResult(Integer quantity, String baseCurrency, String targetCurrency, String interval, String currentDate, List<CurrentExchangeRate> rates) {
        this.quantity = quantity;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.interval = interval;
        this.currentDate = currentDate;
        this.rates = rates;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<CurrentExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<CurrentExchangeRate> rates) {
        this.rates = rates;
    }
}
