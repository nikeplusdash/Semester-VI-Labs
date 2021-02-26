package com.zespr.additionalq.http;

import java.util.List;

public class Data {
    private long dt;
    private long sunrise;
    private long sunset;
    private float temp;
    private float feels_like;
    private int pressure;
    private int humidity;
    private float dew_point;
    private float uvi;
    private int clouds;
    private int visibility;
    private float wind_speed;
    private float wind_deg;
    private List<Weather> weather;

    public float getDew_point() {
        return dew_point;
    }

    public float getUvi() {
        return uvi;
    }

    public float getWind_deg() {
        return wind_deg;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public int getClouds() {
        return clouds;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public float getTemp() {
        return temp;
    }

    public int getVisibility() {
        return visibility;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public long getDt() {
        return dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

}
