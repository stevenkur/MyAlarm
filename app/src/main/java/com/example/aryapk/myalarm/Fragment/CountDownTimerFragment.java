package com.example.aryapk.myalarm.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aryapk.myalarm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountDownTimerFragment extends Fragment {
    @Bind(R.id.clock_picker) com.shawnlin.numberpicker.NumberPicker clockPicker;
    @Bind(R.id.minute_picker) com.shawnlin.numberpicker.NumberPicker minutePicker;
    @Bind(R.id.second_picker) com.shawnlin.numberpicker.NumberPicker secondPicker;
    @Bind(R.id.tvCountDownView) TextView tvCountDownView;
    @Bind(R.id.btnReset) Button reset;
    @Bind(R.id.btnStart) Button start;
    @Bind(R.id.llTimePicker)
    LinearLayout llTimePicker;

    long timeLeft = 0;
    boolean timerRunning;
    CountDownTimer countDownTimer;
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
        ButterKnife.bind(this,v);

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

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        start.setOnClickListener(option);
        return v;
    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnStart:
                    int clockInput  = Integer.parseInt(String.valueOf(clockPicker.getValue()));
                    int minuteInput  = Integer.parseInt(String.valueOf(minutePicker.getValue()));
                    int secondInput  = Integer.parseInt(String.valueOf(secondPicker.getValue()));
                    timeLeft = (long) clockInput * 3600000 +  (long) minuteInput * 60000 + (long) secondInput * 1000;
                    startStop(timeLeft);
                    break;
            }
        }
    };

    private void resetTimer() {
//        timerRunning = false;
//        tvCountDownView.setVisibility(View.GONE);
//        reset.setVisibility(View.GONE);
//        llTimePicker.setVisibility(View.VISIBLE);
//        start.setText("START");
    }

    private void startStop(long timeLeft) {
        if (timerRunning){
            stopTimer();
        }
        else {
            startTimer();
        }
    }

    private void stopTimer() {
//        reset.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        start.setText("START");
        timerRunning = false;
    }

    private void startTimer(){
        tvCountDownView.setVisibility(View.VISIBLE);
        llTimePicker.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                tvCountDownView.setVisibility(View.GONE);
                llTimePicker.setVisibility(View.VISIBLE);
                start.setText("START");
            }
        }.start();
        start.setText("PAUSE");
        timerRunning = true;
    }

    private void updateTimer() {
        int clock = (int)((timeLeft / (1000*60*60))%24);
        int minutes = (int) ((timeLeft/(1000*60))%60);
        int seconds = (int) (timeLeft / 1000) %60;
        String timeLeftText;
        timeLeftText = ""+clock+":"+minutes;
        timeLeftText += ":";
        if (seconds < 10)
            timeLeftText +=0;
        timeLeftText += seconds;

        tvCountDownView.setText(timeLeftText);
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