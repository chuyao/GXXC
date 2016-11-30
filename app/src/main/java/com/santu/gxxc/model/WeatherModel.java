package com.santu.gxxc.model;

import java.io.Serializable;

/**
 * Created by ChuyaoShi on 16/11/21.
 */

public class WeatherModel implements Serializable {

    private String date;
    private String detail;
    private String image;

    public WeatherModel(String date, String detail, String image) {
        this.date = date;
        this.detail = detail;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
