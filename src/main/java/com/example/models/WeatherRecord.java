package com.example.models;

import java.time.Instant;

public class WeatherRecord {
    private String city;
    private double temperature;
    private int humidity;
    private Instant timestamp;

    public WeatherRecord(String city, double temperature, int humidity, Instant timestamp) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
