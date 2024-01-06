package com.jar.storeLedger.model;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateData {

    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String date;
    private String base;
    private Map<String, Double> rates;

}
