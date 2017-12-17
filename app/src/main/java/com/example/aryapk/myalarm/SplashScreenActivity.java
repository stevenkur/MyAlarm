package com.example.aryapk.myalarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.aryapk.myalarm.Adapter.AlarmOverviewModel;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    private static int splashInterval = 2000;
    private ArrayList<AlarmOverviewModel> alarmList = new ArrayList<>();
    private static final int PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        //getAlarm();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestStoragePermission();
                requestWakeLock();
                /*Bundle args = new Bundle();
                args.putSerializable("alarm",alarmList);
                i.putExtras(args);*/
                Intent i = new Intent(SplashScreenActivity.this,AlarmHomeActivity.class);
                startActivity(i);
                finish();
            }
        }, splashInterval);
    }

    /*private void getAlarm(){
        Cursor result = dbMaster.selectAll();
        AlarmOverviewModel model = new AlarmOverviewModel();
        if (result.getCount() > 0){
            for (int i = 0;i<result.getCount();i++){
                if(i==0){
                    result.moveToFirst();
                }
                else {
                    result.moveToNext();
                }
                model.setHour(result.getString(result.getColumnIndex("hour")));
                model.setMinute(result.getString(result.getColumnIndex("minute")));
                model.setSide(result.getString(result.getColumnIndex("side")));
                model.setDate(result.getString(result.getColumnIndex("date")));
                model.setStatus(result.getString(result.getColumnIndex("status")));
                model.setCountDown(result.getInt(result.getColumnIndex("countDown")));
                model.setName(result.getString(result.getColumnIndex("name")));
                alarmList.add(model);
            }
        }
    }*/

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }

    private void requestWakeLock() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED)
            return;

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, PERMISSION_CODE);
    }
}
