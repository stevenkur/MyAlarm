package com.example.aryapk.myalarm.stopwatch;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.aryapk.myalarm.adapters.StopwatchModel;

/**
 * Created by Kurniawan on 18/12/2017.
 */

public class StopwatchResumeState implements StopwatchState {

    @Override
    public Runnable doAction(StopwatchModel stopwatchModel) {
        stopwatchModel.getHandler().postDelayed(stopwatchModel.getRunnable(), 0);
        stopwatchModel.getBtnResumeStopwatch().setVisibility(View.GONE);
        stopwatchModel.getBtnResetStopwatch().setVisibility(View.GONE);
        stopwatchModel.getBtnPauseStopwatch().setVisibility(View.VISIBLE);
        stopwatchModel.getBtnSaveLapStopwatch().setVisibility(View.VISIBLE);
        return stopwatchModel.getRunnable();
    }
}
