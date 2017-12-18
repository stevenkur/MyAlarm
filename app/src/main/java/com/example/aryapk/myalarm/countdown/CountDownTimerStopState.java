package com.example.aryapk.myalarm.countdown;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Farhan Ramadhana on 12/18/2017.
 */

public class CountDownTimerStopState implements CountDownTimerState {
    @Override
    public CountDownTimer doAction(CountDownTimer countDownTimer, TextView tvCountDownView, LinearLayout llTimePicker, Button btnStart, Button btnReset) {
        btnReset.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        btnStart.setText("CONTINUE");
        return countDownTimer;
    }
}
