package com.example.services;

import com.example.models.WeatherRecord;
import com.example.stockage.WeatherStockage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class WeatherAnalysisService {

    @Autowired
    private final WeatherStockage store;

    public WeatherAnalysisService(WeatherStockage store) {
        this.store = store;
    }

    public double averageTemperature(String city) {
        List<WeatherRecord> records = store.findByCity(city);
        return records.stream().mapToDouble(WeatherRecord::getTemperature).average().orElse(Double.NaN);
    }

    public String tendance(String city) {
        List<WeatherRecord> records = store.findByCity(city);
        if (records.size() < 2) return "NOT_ENOUGH_DATA";

        records.sort(Comparator.comparing(WeatherRecord::getTimestamp));
        double first = records.get(0).getTemperature();
        double last = records.get(records.size() - 1).getTemperature();

        if (last > first) return "UP";
        if (last < first) return "DOWN";
        return "STABLE";
    }
}

