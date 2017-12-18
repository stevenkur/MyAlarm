package com.example.aryapk.myalarm.adapters;

import android.os.Handler;
import android.widget.Button;

/**
 * Created by Kurniawan on 18/12/2017.
 */

public class StopwatchModel {
    private Runnable runnable;
    private Handler handler;
    private Button btnStartStopwatch;
    private Button btnPauseStopwatch;
    private Button btnResumeStopwatch;
    private Button btnResetStopwatch;
    private Button btnSaveLapStopwatch;

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Button getBtnStartStopwatch() {
        return btnStartStopwatch;
    }

    public void setBtnStartStopwatch(Button btnStartStopwatch) {
        this.btnStartStopwatch = btnStartStopwatch;
    }

    public Button getBtnPauseStopwatch() {
        return btnPauseStopwatch;
    }

    public void setBtnPauseStopwatch(Button btnPauseStopwatch) {
        this.btnPauseStopwatch = btnPauseStopwatch;
    }

    public Button getBtnResumeStopwatch() {
        return btnResumeStopwatch;
    }

    public void setBtnResumeStopwatch(Button btnResumeStopwatch) {
        this.btnResumeStopwatch = btnResumeStopwatch;
    }

    public Button getBtnResetStopwatch() {
        return btnResetStopwatch;
    }

    public void setBtnResetStopwatch(Button btnResetStopwatch) {
        this.btnResetStopwatch = btnResetStopwatch;
    }

    public Button getBtnSaveLapStopwatch() {
        return btnSaveLapStopwatch;
    }

    public void setBtnSaveLapStopwatch(Button btnSaveLapStopwatch) {
        this.btnSaveLapStopwatch = btnSaveLapStopwatch;
    }
}
