package com.example.aryapk.myalarm;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;
import com.example.aryapk.myalarm.HomeFunctionals.HomeAlarmAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeAlarmActivity extends AppCompatActivity {
    @Bind(R.id.rvListAlarm)
    RecyclerView rvListAlarm;
    ArrayList<AlarmOverviewModel> modelAlarmOverview = new ArrayList<>();
    HomeAlarmAdapter adapter;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_alarm);
        ButterKnife.bind(this);
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
