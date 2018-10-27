package com.xaymaca.poc.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDate;

/**
 * Created by Vincent Stoessel on July 07, 2016.
 */
public class CurrentExchangeRate implements Comparable<CurrentExchangeRate> {


    String date;
    Float value;
    @JsonIgnore
    LocalDate localDate;




    public CurrentExchangeRate(String date, Float value, LocalDate localDate ) {
        this.date = date;
        this.value = value;
        this.localDate = localDate;

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public int compareTo(CurrentExchangeRate anotherRate) {

        return anotherRate.getLocalDate().compareTo(this.getLocalDate());

     }


    @Override
    public String toString() {
        return "CurrentExchangeRate{" +
                "date='" + date + '\'' +
                ", value=" + value +
                ", localDate=" + localDate +
                '}';
    }
}
