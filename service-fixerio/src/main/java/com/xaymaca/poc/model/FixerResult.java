package com.xaymaca.poc.model;

import java.util.Map;

/**
 * Created by Vincent Stoessel on July 08, 2016.
 */
public class FixerResult {
    /*
    {"base":"GBP","date":"2015-07-31","rates":{"USD":1.5576}}
     */


    String base;
    String date;
    Map<String,Float> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }
}
