package com.xaymaca.poc.model;

import java.util.List;

/**
 * Created by Vincent Stoessel on July 08, 2016.
 *
 */
public class EventsResult {


    Integer quantity;
    String baseCurrency;
    String targetCurrency;
    String eventDate;

    List<CurrentExchangeRate> rates;


    public EventsResult() {
    }


    public EventsResult(Integer quantity, String baseCurrency, String targetCurrency, String eventDate, List<CurrentExchangeRate> rates) {
        this.quantity = quantity;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.eventDate = eventDate;
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public List<CurrentExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<CurrentExchangeRate> rates) {
        this.rates = rates;
    }
}
