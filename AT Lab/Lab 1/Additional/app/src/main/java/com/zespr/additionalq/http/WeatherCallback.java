package com.zespr.additionalq.http;

import java.util.List;

public interface WeatherCallback {
    void onSuccess(Result[] weather);
    void onError();
}
