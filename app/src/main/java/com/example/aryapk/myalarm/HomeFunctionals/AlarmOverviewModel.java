package com.example.aryapk.myalarm.HomeFunctionals;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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
    @SerializedName("countdown") private CountDownTimer countDown;

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

    public CountDownTimer getCountDown() {
        return countDown;
    }

    public void setCountDown(CountDownTimer countDown) {
        this.countDown = countDown;
    }

    public long countDuration(){
        long duration = 0;
        Date currentTime = Calendar.getInstance().getTime();
        long currentHour=currentTime.getHours();
        long currentMinute=currentTime.getMinutes();
        long currentSecond=currentTime.getSeconds();
        long selectedHour = Long.parseLong(hour);
        long selectedMinute = Long.parseLong(minute);
        Log.i("Current Time",currentHour+ " : "+ currentMinute);
        Log.i("Selected Time",selectedHour+ " : "+ selectedMinute);
        if (currentHour>selectedHour){
            selectedHour+=24;
        }
        long currentSeconds = currentHour*3600 + currentMinute*60 + currentSecond;
        long currentMSecond = currentSeconds*1000;
        long selectedSeconds = selectedHour*3600 + selectedMinute*60;
        long selectedMSecond = selectedSeconds*1000;
        duration = selectedMSecond-currentMSecond;
        return duration;
    }

    public CountDownTimer createCountDown(final Context context){
        long duration = countDuration();
        Log.i("path 2",path);
        final Uri songUri = Uri.parse(path);
        long toSum = duration;
        Log.i("Duration", String.valueOf(duration));
        long hourTotal = toSum/3600000;
        toSum = toSum - hourTotal*3600000;
        long minuteTotal = toSum/60000;
        Toast.makeText(context,"Alarm set for "+String.valueOf(hourTotal)+" hours and "+String.valueOf(minuteTotal)+" minutes from now",Toast.LENGTH_LONG).show();
        CountDownTimer countDownTimer =
                new CountDownTimer(duration, 1000) {
                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        Log.i("CountDown","finished");
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mediaPlayer.setDataSource(context, songUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                };
        return countDownTimer;
    }
}
