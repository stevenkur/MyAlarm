package com.example.aryapk.myalarm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.aryapk.myalarm.DBAlarm.DatabaseMaster;
import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;

public class SplashScreenActivity extends AppCompatActivity {

    private static int splashInterval = 2000;
    private DatabaseMaster dbMaster = new DatabaseMaster(SplashScreenActivity.this);
    private AlarmOverviewModel model = new AlarmOverviewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getAlarm();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle args = new Bundle();
                args.putSerializable("alarm",model);
                Intent i = new Intent(SplashScreenActivity.this,SelectModeActivity.class);
                i.putExtras(args);
                startActivity(i);
                finish();
            }
        }, splashInterval);
    }

    private void getAlarm(){
        Cursor result = dbMaster.selectAll();
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
            }
        }
    }
}
