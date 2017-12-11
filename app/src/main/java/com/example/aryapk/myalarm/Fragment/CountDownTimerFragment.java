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
import android.widget.EditText;
import android.widget.TextView;

import com.example.aryapk.myalarm.R;

public class CountDownTimerFragment extends Fragment {
    TextView countDownText;
    Button countDownBtn;
    CountDownTimer countDownTimer;
    EditText minuteText;
    boolean timerRunning;
    long timeLeft = 0;
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
        countDownText = (TextView) v.findViewById(R.id.countDown);
        minuteText = (EditText) v.findViewById(R.id.minuteText);
        countDownBtn = (Button) v.findViewById(R.id.btnCount);

        countDownBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int timeInput  = Integer.parseInt(minuteText.getText().toString());
                timeLeft = (long) timeInput * 60000;
                startStop(timeLeft);
            }
        });
        return v;
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
        countDownBtn.setText("PAUSE");
        timerRunning = true;
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

    private void stopTimer() {
        countDownTimer.cancel();
        countDownBtn.setText("START");
        timerRunning = false;
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
