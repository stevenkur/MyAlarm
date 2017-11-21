package com.example.aryapk.myalarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewAlarmActivity extends AppCompatActivity {
    @Bind(R.id.btnSaveAlarm)
    Button btnSaveAlarm;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    Context activity;

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.btnSaveAlarm:
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
        btnSaveAlarm.setOnClickListener(option);
        btnCancel.setOnClickListener(option);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(activity, HomeAlarmActivity.class);
        startActivity(i);
        finish();
    }
}
