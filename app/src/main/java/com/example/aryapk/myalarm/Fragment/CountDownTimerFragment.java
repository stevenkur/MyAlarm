package com.example.aryapk.myalarm.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompatApi24;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aryapk.myalarm.R;

public class CountDownTimerFragment extends Fragment {
    com.shawnlin.numberpicker.NumberPicker clockPicker, minutePicker, secondPicker ;
    Button reset, start;
    View v;
    public CountDownTimerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_count_down_timer, container, false);
        // find picker id
        clockPicker= (com.shawnlin.numberpicker.NumberPicker) v.findViewById(R.id.clock_picker);
        minutePicker= (com.shawnlin.numberpicker.NumberPicker) v.findViewById(R.id.minute_picker);
        secondPicker = (com.shawnlin.numberpicker.NumberPicker) v.findViewById(R.id.second_picker);
        //find button id
        reset = (Button) v.findViewById(R.id.btnReset);
        start = (Button) v.findViewById(R.id.btnStart);
        //clock picker setting
        clockPicker.setMinValue(0);
        clockPicker.setMaxValue(99);
        clockPicker.setWrapSelectorWheel(true);
        //minute picker setting
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setWrapSelectorWheel(true);
        //second picker setting
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        secondPicker.setWrapSelectorWheel(true);
        return v;
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
        void onFragmentInteraction(Uri uri);
    }
}
