package com.example.aryapk.myalarm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.aryapk.myalarm.DBAlarm.DatabaseMaster;
import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.aryapk.myalarm.R.id.time;

public class NewAlarmActivity extends AppCompatActivity {
    @Bind(R.id.btnSaveAlarm)
    Button btnSaveAlarm;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.tvRepeat)
    TextView tvRepeat;
    @Bind(R.id.tvSnooze)
    TextView tvSnooze;
    @Bind(R.id.tvSound)
    TextView tvSound;
    @Bind(R.id.tvTone)
    TextView tvTone;
    @Bind(R.id.tpClock)
    TimePicker tpClock;

    Context activity;
    private Uri filePath;
    private int PICK_SOUND_REQUEST = 1;
    private DatabaseMaster dbMaster = new DatabaseMaster(NewAlarmActivity.this);
    ArrayList<AlarmOverviewModel> alarmList = new ArrayList<>();

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.btnSaveAlarm:
                    getSound();
                    i = new Intent(activity, HomeAlarmActivity.class);
                    startActivity(i);
                    break;
                case R.id.btnCancel:
                    i = new Intent(activity, HomeAlarmActivity.class);
                    startActivity(i);
                    break;
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        ButterKnife.bind(this);
        activity = this;
        showFileChooser();
        btnSaveAlarm.setOnClickListener(option);
        btnCancel.setOnClickListener(option);
        getAlarm();
    }

    private void getAlarm() {
        Cursor result = dbMaster.selectAll();
        AlarmOverviewModel newAlarm = new AlarmOverviewModel();
        if (result.getCount() > 0){
            for (int i = 0;i<result.getCount();i++){
                if(i==0){
                    result.moveToFirst();
                }
                else {
                    result.moveToNext();
                }
                newAlarm.setHour(result.getString(result.getColumnIndex("hour")));
                newAlarm.setMinute(result.getString(result.getColumnIndex("minute")));
                newAlarm.setSide(result.getString(result.getColumnIndex("side")));
                newAlarm.setDate(result.getString(result.getColumnIndex("date")));
                newAlarm.setStatus(result.getString(result.getColumnIndex("status")));
                newAlarm.setCountDown(result.getInt(result.getColumnIndex("countDown")));
                newAlarm.setName(result.getString(result.getColumnIndex("name")));
                alarmList.add(newAlarm);
            }
        }
    }

    /*private void insertAlarm(){


        TimePicker timePicker;
        timePicker = (TimePicker) findViewById(R.id.tpClock);

        tpClock.clearFocus();

        timePicker.hour   = tpClock.getCurrentHour();
        timePicker.minute = tpClock.getCurrentMinute();
    }*/

    private void getSound(){
        /*try {
            showFileChooser();
        } finally {

        }*/
        String path = FilePath.getPath(this,filePath);
        setAlarm(path);
    }

    private void setAlarm(String path){
        Date currentTime = Calendar.getInstance().getTime();
        long currentHour=currentTime.getHours();
        long currentMinute=currentTime.getMinutes();
        long currentSecond=currentTime.getSeconds();
        long selectedHour = tpClock.getHour();
        long selectedMinute = tpClock.getMinute();
        Log.i("Current Time",currentHour+ " : "+ currentMinute);
        Log.i("Selected Time",selectedHour+ " : "+ selectedMinute);
        if (currentHour>selectedHour){
            selectedHour+=24;
        }
        long currentSeconds = currentHour*3600 + currentMinute*60 + currentSecond;
        long currentMSecond = currentSeconds*1000;
        long selectedSeconds = selectedHour*3600 + selectedMinute*60;
        long selectedMSecond = selectedSeconds*1000;
        long duration = selectedMSecond-currentMSecond;
        Log.i("Duration", String.valueOf(duration));
        /*AlarmTurnOn alarmTurnOn = new AlarmTurnOn();
        alarmTurnOn.setSoundPath(path);
        alarmTurnOn.setTimeCount(duration);
        Log.i("Path",path);
        new PlaySound(activity).execute(alarmTurnOn);*/

        turnOn(path,duration);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_SOUND_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_SOUND_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    private void turnOn(String paths, long duration){
        String path = paths;
        Log.i("path 2",path);
        final Uri songUri = Uri.parse(path);
        new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Log.i("CountDown","finished");
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(activity, songUri);
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
        }.start();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(activity, HomeAlarmActivity.class);
        startActivity(i);
        finish();
    }


}
