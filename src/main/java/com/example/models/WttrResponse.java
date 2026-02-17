package com.example.models;

import java.util.List;

public class WttrResponse {

    private List<CurrentCondition> current_condition;

    public List<CurrentCondition> getCurrent_condition() {
        return current_condition;
    }
    public void setCurrent_condition(List<CurrentCondition> current_condition) {
        this.current_condition = current_condition;
    }

    public static class CurrentCondition {
        private String temp_C;
        private String humidity;

        public String getTemp_C() { return temp_C; }
        public void setTemp_C(String temp_C) { this.temp_C = temp_C; }

        public String getHumidity() { return humidity; }
        public void setHumidity(String humidity) { this.humidity = humidity; }
    }
}
