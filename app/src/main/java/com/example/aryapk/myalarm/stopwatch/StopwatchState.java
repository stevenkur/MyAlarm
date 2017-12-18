package com.example.aryapk.myalarm.stopwatch;

import android.os.Handler;
import android.widget.Button;

import com.example.aryapk.myalarm.adapters.StopwatchModel;

/**
 * Created by Kurniawan on 17/12/2017.
 */

public interface StopwatchState {
    public Runnable doAction(StopwatchModel stopwatchModel);
}
