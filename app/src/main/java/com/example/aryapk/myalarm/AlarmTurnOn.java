package com.example.aryapk.myalarm;

import java.io.Serializable;

/**
 * Created by Aryapk on 12/6/2017.
 */

public class AlarmTurnOn implements Serializable {
    private long timeCount;
    private String soundPath;

    public long getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(long timeCount) {
        this.timeCount = timeCount;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }
}
