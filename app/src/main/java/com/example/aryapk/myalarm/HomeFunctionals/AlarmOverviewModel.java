package com.example.aryapk.myalarm.HomeFunctionals;

import java.io.Serializable;

/**
 * Created by Aryapk on 11/12/2017.
 */

public class AlarmOverviewModel implements Serializable {
    private String hour;
    private String minute;
    private String side;
    private String date;
    private String status;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
