package com.zespr.additionalq.http;

import java.util.List;

public class WeatherData {
    private float lat;
    private float lon;
    private String timezone;
    private int timezone_offset;
    private Data current;
    private List<Data> hourly;

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }

    public Data getCurrent() {
        return current;
    }

    public List<Data> getHourly() {
        return hourly;
    }
}
