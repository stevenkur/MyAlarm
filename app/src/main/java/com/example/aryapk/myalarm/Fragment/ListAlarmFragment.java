package com.example.aryapk.myalarm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aryapk.myalarm.DummyUtils;
import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;
import com.example.aryapk.myalarm.HomeFunctionals.HomeAlarmAdapter;
import com.example.aryapk.myalarm.NewAlarmActivity;
import com.example.aryapk.myalarm.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListAlarmFragment extends Fragment {
    View v;

    @Bind(R.id.rvListAlarm)
    RecyclerView rvListAlarm;
    @Bind(R.id.btnNewAlarm)
    Button btnNewAlarm;
    ArrayList<AlarmOverviewModel> alarmList = new ArrayList<>();
    HomeAlarmAdapter adapter;
    Context activity;

    public ListAlarmFragment() {

    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNewAlarm:
                    Intent i = new Intent(activity, NewAlarmActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyAlarm();
        btnNewAlarm.setOnClickListener(option);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_alarm, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    private void setAlarm(){
        rvListAlarm.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new HomeAlarmAdapter(alarmList);
    }

    private void dummyAlarm() {
        rvListAlarm.setLayoutManager(new LinearLayoutManager(activity));
        DummyUtils utils = new DummyUtils();
        alarmList = utils.getDummyModel();
        adapter = new HomeAlarmAdapter(alarmList);
        rvListAlarm.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
