package com.example.stockage;

import com.example.models.WeatherRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherStockage {

    private final List<WeatherRecord> records = new ArrayList<>();

    public synchronized void save(WeatherRecord record) {
        records.add(record);
    }

    public synchronized List<WeatherRecord> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(records));
    }

    public synchronized List<WeatherRecord> findByCity(String city) {
        return records.stream()
                .filter(r -> r.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}
