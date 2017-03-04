package com.example.poojaandsaba.weatherapp;
/**
 * Created by poojaandsaba on 10/12/15.
 */
public class CandyForHour {

    private String time;
    private String temperatVal;
    private String icon;

    public CandyForHour(String temperatVal, String icon, String time) {

        this.time = time;
        this.temperatVal = temperatVal;
        this.icon = icon;
    }
    public String getIconId() {

        return icon;
    }
    public String getTime() {

        return time;
    }

    public String getTemperatVal() {

        return temperatVal;
    }
}

