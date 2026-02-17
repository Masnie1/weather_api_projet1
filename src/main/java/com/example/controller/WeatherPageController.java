package com.example.controller;

import com.example.services.WeatherAnalysisService;
import com.example.services.WeatherApiService;
import com.example.stockage.WeatherStockage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherPageController {

    private final WeatherApiService api;
    private final WeatherStockage store;
    private final WeatherAnalysisService analysis;

    public WeatherPageController(WeatherApiService api, WeatherStockage store, WeatherAnalysisService analysis) {
        this.api = api;
        this.store = store;
        this.analysis = analysis;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String city, Model model) {
        model.addAttribute("records", store.findAll());

        if (city != null && !city.isBlank()) {
            try {
                var record = api.fetchCurrent(city);
                store.save(record);

                model.addAttribute("latest", record);
                model.addAttribute("city", city);
                model.addAttribute("avg", analysis.averageTemperature(city));
                model.addAttribute("trend", analysis.tendance(city));
            } catch (Exception e) {
                model.addAttribute("city", city);
                model.addAttribute("error", "Erreur météo pour '" + city + "': " + e.getMessage());
            }
        }
        return "index";
    }
}
