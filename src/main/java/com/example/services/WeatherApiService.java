package com.example.services;

import com.example.models.WeatherRecord;
import com.example.models.WttrResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
public class WeatherApiService {

    private final WebClient webClient;

    public WeatherApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public WeatherRecord fetchCurrent(String city) {
        WttrResponse dto = webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/{city}")
                        .queryParam("format", "j1")
                        .build(city))
                        .retrieve()
                        .bodyToMono(WttrResponse.class)
                        .block();

        if (dto == null || dto.getCurrent_condition() == null || dto.getCurrent_condition().isEmpty()) {
            throw new IllegalStateException("Invalid API response for city=" + city);
        }

        var current = dto.getCurrent_condition().get(0);
        double tempC = Double.parseDouble(current.getTemp_C());
        int humidity = Integer.parseInt(current.getHumidity());

        return new WeatherRecord(city, tempC, humidity, Instant.now());
    }
}
