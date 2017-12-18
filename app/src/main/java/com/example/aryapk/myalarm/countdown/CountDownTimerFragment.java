package com.example.aryapk.myalarm.countdown;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.aryapk.myalarm.MyService;
import com.example.aryapk.myalarm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountDownTimerFragment extends Fragment {
    @Bind(R.id.hour_picker)
    com.shawnlin.numberpicker.NumberPicker hourPicker;
    @Bind(R.id.minute_picker)
    com.shawnlin.numberpicker.NumberPicker minutePicker;
    @Bind(R.id.second_picker)
    com.shawnlin.numberpicker.NumberPicker secondPicker;
    @Bind(R.id.tvCountDownView)
    TextView tvCountDownView;
    @Bind(R.id.btnReset)
    Button btnReset;
    @Bind(R.id.btnStart)
    Button btnStart;
    @Bind(R.id.llTimePicker)
    LinearLayout llTimePicker;
    Context context;

    CountDownTimer timer;

    long timeLeft = 0;
    long timeSaved = 0;

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
        v = inflater.inflate(R.layout.fragment_count_down_timer, container, false);
        ButterKnife.bind(this, v);

        //clock picker setting
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(99);
        hourPicker.setWrapSelectorWheel(true);
        //minute picker setting
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setWrapSelectorWheel(true);
        //second picker setting
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        secondPicker.setWrapSelectorWheel(true);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        btnStart.setOnClickListener(option);
        return v;
    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart:
                    if (btnStart.getText().equals("START") || btnStart.getText().equals("CONTINUE")) {
                        if (timeSaved == 0) {
                            int hourInput = Integer.parseInt(String.valueOf(hourPicker.getValue()));
                            int minuteInput = Integer.parseInt(String.valueOf(minutePicker.getValue()));
                            int secondInput = Integer.parseInt(String.valueOf(secondPicker.getValue()));
                            timeLeft = (long) hourInput * 3600000 + (long) minuteInput * 60000 + (long) secondInput * 1000 + 1000;
                            timeSaved = timeLeft;
                        }
                        CountDownTimerStartState countDownTimerStartState = new CountDownTimerStartState();
                        timer = countDownTimerStartState.doAction(startCountDown(), tvCountDownView, llTimePicker, btnStart, btnReset);
                    } else if (btnStart.getText().equals("PAUSE")) {
                        CountDownTimerStopState countDownTimerStopState = new CountDownTimerStopState();
                        timer = countDownTimerStopState.doAction(timer, tvCountDownView, llTimePicker, btnStart, btnReset);
                    }
                    break;
            }
        }
    };

    private void resetTimer() {
        timeSaved = 0;
        timeLeft = 0;
        llTimePicker.setVisibility(View.VISIBLE);
        tvCountDownView.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        timer.cancel();
        btnStart.setText("START");
    }

    public CountDownTimer startCountDown(){
        CountDownTimer countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeSaved -= l;
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Bundle bundle = new Bundle();
                bundle.putString("extra","default");
                Intent intent = new Intent(getContext(),MyService.class);
                intent.putExtras(bundle);
                getActivity().startService(intent);
                createDialog();
            }
        };
        return countDownTimer;
    }

    private void updateTimer() {
        int hour = (int) ((timeLeft / (1000 * 60 * 60)) % 24);
        int minute = (int) ((timeLeft / (1000 * 60)) % 60);
        int second = (int) (timeLeft / 1000) % 60;
        String timeLeftText = null;
        String hourText = null;
        String minuteText = null;
        String secondText = null;

        if (hour < 10) {
            hourText = "0" + String.valueOf(hour);
        } else {
            hourText = String.valueOf(hour);
        }
        if (minute < 10) {
            minuteText = "0" + String.valueOf(minute);
        } else {
            minuteText = String.valueOf(minute);
        }
        if (second < 10) {
            secondText = "0" + String.valueOf(second);
        } else {
            secondText = String.valueOf(second);
        }
        timeLeftText = hourText + ":" + minuteText + ":" + secondText;
        tvCountDownView.setText(timeLeftText);
    }

    private void createDialog() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        alertDialog.setTitle("Timer Done");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().stopService(new Intent(getActivity(), MyService.class));
                timeSaved = 0;
                timeLeft = 0;
                timer.cancel();
                tvCountDownView.setVisibility(View.GONE);
                btnReset.setVisibility(View.GONE);
                llTimePicker.setVisibility(View.VISIBLE);
                btnStart.setText("START");
            }
        });

        alertDialog.show();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}