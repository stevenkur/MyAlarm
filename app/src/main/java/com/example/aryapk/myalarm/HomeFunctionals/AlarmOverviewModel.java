package com.example.aryapk.myalarm.HomeFunctionals;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Aryapk on 11/12/2017.
 */

public class AlarmOverviewModel implements Serializable {
    @SerializedName("date") private String date;
    @SerializedName("hour") private String hour;
    @SerializedName("minute") private String minute;
    @SerializedName("name") private String name;
    @SerializedName("path") private String path;
    @SerializedName("status") private String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
