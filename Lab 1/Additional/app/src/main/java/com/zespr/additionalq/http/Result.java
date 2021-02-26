package com.zespr.additionalq.http;

public class Result {
    private float temp;
    private float feels_like;
    private String img;

    public float getFeels_like() {
        return feels_like;
    }

    public float getTemp() {
        return temp;
    }

    public String getImg() {
        return img;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
