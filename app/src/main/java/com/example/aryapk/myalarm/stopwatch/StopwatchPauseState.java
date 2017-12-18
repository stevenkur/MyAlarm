package com.example.aryapk.myalarm.stopwatch;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.aryapk.myalarm.adapters.StopwatchModel;

/**
 * Created by Kurniawan on 18/12/2017.
 */

public class StopwatchPauseState implements StopwatchState {

    @Override
    public Runnable doAction(StopwatchModel stopwatchModel) {
        stopwatchModel.getHandler().removeCallbacks(stopwatchModel.getRunnable());
        stopwatchModel.getBtnPauseStopwatch().setVisibility(View.GONE);
        stopwatchModel.getBtnResumeStopwatch().setVisibility(View.VISIBLE);
        stopwatchModel.getBtnResetStopwatch().setVisibility(View.VISIBLE);
        stopwatchModel.getBtnSaveLapStopwatch().setVisibility(View.GONE);
        return stopwatchModel.getRunnable();
    }
}
