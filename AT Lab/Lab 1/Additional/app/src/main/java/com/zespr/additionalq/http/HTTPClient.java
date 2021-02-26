package com.zespr.additionalq.http;

import android.util.Log;

import java.util.concurrent.Executor;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HTTPClient {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/onecall/timemachine";
    private static final String URL = BASE_URL + '?' + "lat=" + 13.360501 + "&lon=" + 74.786369 + "&appid=&units=metric" + "&dt=";
    private long DT;
    private OkHttpClient client;
    private Gson gson;
    private Result result[] = new Result[5];
    public HTTPClient(long epoch) {
        DT = epoch;
        client = new OkHttpClient();
        gson = new Gson();
    }
    public void loadWeather(WeatherCallback callback) {
        int i = 0;
        while(i < 5){
            Request request = new Request.Builder().get().url(URL + DT).build();
            try {
                Response response = client.newCall(request).execute();
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String json = responseBody.string();
                    WeatherData weatherData = gson.fromJson(json, WeatherData.class);
                    if (weatherData != null) {
                        result[i] = new Result();
                        result[i].setTemp(weatherData.getCurrent().getTemp());
                        result[i].setFeels_like(weatherData.getCurrent().getFeels_like());
                        result[i].setImg(weatherData.getCurrent().getWeather().get(0).getIcon());
                    }
                }
            } catch (IOException e) { // 5
                Log.e("HTTPClient", "Error loading weather", e);
            }
            DT -= 86400;i++;
        }
        callback.onSuccess(result);
    }
}
