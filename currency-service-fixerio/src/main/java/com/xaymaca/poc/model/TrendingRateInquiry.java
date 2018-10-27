package com.xaymaca.poc.model;

/**
 * Created by Vincent Stoessel on July 07, 2016.
 * Pojo for querying the rate
 */
public class TrendingRateInquiry {




    Integer quantity;
    String baseCurrency;
    String targetCurrency;
    String interval ;  // now, month, 3months, year


    public TrendingRateInquiry(Integer quantity, String baseCurrency, String targetCurrency, String interval) {
        this.quantity = quantity;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.interval = interval;
    }

    public TrendingRateInquiry() {

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

}
