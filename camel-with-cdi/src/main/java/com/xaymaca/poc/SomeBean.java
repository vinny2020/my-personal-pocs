package com.xaymaca.poc;

import jakarta.inject.Singleton;
import jakarta.inject.Named;

@Singleton
@Named("counterBean")
public class SomeBean {
    private int counter;

    public int count() {
        return ++counter;
    }
}
