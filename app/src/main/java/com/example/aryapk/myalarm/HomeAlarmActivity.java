package com.example.aryapk.myalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;
import com.example.aryapk.myalarm.HomeFunctionals.HomeAlarmAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeAlarmActivity extends AppCompatActivity {
    @Bind(R.id.rvListAlarm)
    RecyclerView rvListAlarm;
    @Bind(R.id.btnNewAlarm)
    Button btnNewAlarm;
    ArrayList<AlarmOverviewModel> modelAlarmOverview = new ArrayList<>();
    HomeAlarmAdapter adapter;
    Context activity;
    View v;

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNewAlarm:
                    Intent i = new Intent(activity, NewAlarmActivity.class);
                    startActivity(i);
                    break;
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_alarm);
        ButterKnife.bind(this);
        activity = this;
        btnNewAlarm.setOnClickListener(option);
        dummyAlarm();
    }

    private void dummyAlarm() {
        rvListAlarm.setLayoutManager(new LinearLayoutManager(this));
        DummyUtils utils = new DummyUtils();
        modelAlarmOverview = utils.getDummyModel();
        adapter = new HomeAlarmAdapter(modelAlarmOverview);
        rvListAlarm.setAdapter(adapter);
    }
}
