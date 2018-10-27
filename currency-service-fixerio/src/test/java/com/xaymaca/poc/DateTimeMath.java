package com.xaymaca.poc;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Stoessel on July 08, 2016.
 *
 */
public class DateTimeMath {


    @Test
    public void testTrendToday() {

        DateTime today = new DateTime();
        List<String> queryDates = new ArrayList<>();

        for(int i =0; i<=14; i++) {

            String date =  today.minusDays(i).toLocalDate().toString();

            System.out.println("http://api.fixer.io/" + date  ) ;


        }

    }

    @Test
    public void testTrendMonth() {

        DateTime today = new DateTime();
        List<String> queryDates = new ArrayList<>();

        for(int i =0; i<=30; i++) {

            String date =  today.minusDays(i).toLocalDate().toString();

            System.out.println("http://api.fixer.io/" + date  ) ;


        }

    }



    @Test
    public void testTrend3Month() {

        DateTime today = new DateTime();
        List<String> queryDates = new ArrayList<>();

        for(int i =0; i<=90; i++) {

            String date =  today.minusDays(i).toLocalDate().toString();

            System.out.println("http://api.fixer.io/" + date  ) ;


        }

    }



    @Test
    public void testTrendYear() {

        DateTime today = new DateTime();
        List<String> queryDates = new ArrayList<>();

        for(int i =0; i<=365; i++) {

            String date =  today.minusDays(i).toLocalDate().toString();

            System.out.println("http://api.fixer.io/" + date  ) ;


        }

    }

}
