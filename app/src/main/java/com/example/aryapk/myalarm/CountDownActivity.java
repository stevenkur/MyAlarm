package com.example.aryapk.myalarm;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CountDownActivity extends AppCompatActivity {
    TextView countDownText;
    Button countDown1Btn;
    CountDownTimer countDownTimer;
    EditText minuteText;
    boolean timerRunning;
    long timeLeft = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
    }

    private void startStop(long timeLeft) {
        if (timerRunning){
            stopTimer();
        }
        else {
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        countDown1Btn.setText("PAUSE");
        timerRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        countDown1Btn.setText("START");
        timerRunning = false;
    }

    private void updateTimer() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeLeftText;
        timeLeftText = ""+minutes;
        timeLeftText += ":";
        if (seconds < 10)
            timeLeftText +=0;
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);
    }
}
