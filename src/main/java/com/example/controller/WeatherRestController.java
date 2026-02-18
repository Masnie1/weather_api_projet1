package com.example.controller;

import com.example.models.WeatherRecord;
import com.example.services.WeatherAnalysisService;
import com.example.services.WeatherApiService;
import com.example.stockage.WeatherStockage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherRestController {

    private WeatherApiService api;
    @Autowired
    private  WeatherStockage store;
    private final WeatherAnalysisService analysis;

    public WeatherRestController(WeatherApiService api, WeatherStockage store, WeatherAnalysisService analysis) {
        this.api = api;
        this.store = store;
        this.analysis = analysis;
    }

    // 1) GET : fetch via API + save
    @GetMapping("/{city}")
    public WeatherRecord fetchAndSave(@PathVariable String city) {
        WeatherRecord record = api.fetchCurrent(city);
        store.save(record);
        return record;
    }

    // 2) GET : all records stored
    @GetMapping
    public List<WeatherRecord> all() {
        return store.findAll();
    }

    // 3) GET : records for city
    @GetMapping("/{city}/records")
    public List<WeatherRecord> byCity(@PathVariable String city) {
        return store.findByCity(city);
    }

    // 4) GET : average temp
    @GetMapping("/{city}/average")
    public Map<String, Object> average(@PathVariable String city) {
        return Map.of(
                "city", city,
                "averageTemperatureC", analysis.averageTemperature(city)
        );
    }

    // 5) GET : trend
    @GetMapping("/{city}/trend")
    public Map<String, Object> trend(@PathVariable String city) {
        return Map.of(
                "city", city,
                "trend", analysis.tendance(city)
        );
    }

    // batch de villes
    @GetMapping("/batch")
    public List<WeatherRecord> batch(@RequestParam String cities) {
        return Arrays.stream(cities.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(api::fetchCurrent)   // => GET pour chaque ville
                .peek(store::save)
                .toList();
    }
}
