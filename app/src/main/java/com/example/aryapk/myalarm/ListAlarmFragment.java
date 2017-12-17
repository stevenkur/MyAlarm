package com.example.aryapk.myalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aryapk.myalarm.Adapter.AlarmOverviewModel;
import com.example.aryapk.myalarm.Adapter.AlarmAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    AlarmAdapter adapter;
    Context activity;
    int flag = 0;

    public ListAlarmFragment() {

    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNewAlarm:
                    Intent i = new Intent(activity, NewAlarmActivity.class);
                    startActivity(i);
                    ((AlarmHomeActivity)activity).finish();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_alarm, container, false);
        ButterKnife.bind(this,v);
        btnNewAlarm.setOnClickListener(option);
        rvListAlarm.setLayoutManager(new LinearLayoutManager(activity));
        getPreferenceListAlarm();
        return v;
    }

    private void setAlarm(){
        adapter = new AlarmAdapter(alarmList,activity);
    }

    private void createSharedPreference(){
        SharedPreferences mySharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = mySharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarmList);
        edt.putString("alarmList",json);
        edt.commit();
    }

    private void getPreferenceListAlarm(){
        alarmList.removeAll(alarmList);
        SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("alarmDB",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mySharedPreferences.getString("alarmList","Empty");
        Type type = new TypeToken<ArrayList<AlarmOverviewModel>>(){}.getType();
        if (json != "Empty"){
            alarmList = gson.fromJson(json,type);
            adapter = new AlarmAdapter(alarmList,activity);
            rvListAlarm.setAdapter(adapter);
        }
        else Log.i("ListAlarm","isEmpty");
        setAlarm();
    }


    private void dummyAlarm() {
        rvListAlarm.setLayoutManager(new LinearLayoutManager(activity));
        DummyUtils utils = new DummyUtils();
        alarmList = utils.getDummyModel();
        adapter = new AlarmAdapter(alarmList,activity);
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
