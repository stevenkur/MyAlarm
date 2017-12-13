package com.example.aryapk.myalarm.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;
import com.example.aryapk.myalarm.HomeFunctionals.HomeAlarmAdapter;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_alarm, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
