package com.example.aryapk.myalarm.stopwatch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aryapk.myalarm.adapters.StopwatchAdapter;
import com.example.aryapk.myalarm.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StopwatchFragment extends Fragment {

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    ArrayList<String> listStopwatch = new ArrayList<>();

    StopwatchAdapter adapter;

    Context context;

    int Seconds, Minutes, MilliSeconds ;

    View v;
    @Bind(R.id.tvStopwatch)
    TextView tvStopwatch;
    @Bind(R.id.btnStartStopwatch)
    Button btnStartStopwatch;
    @Bind(R.id.btnPauseStopwatch)
    Button btnPauseStopwatch;
    @Bind(R.id.btnResetStopwatch)
    Button btnResetStopwatch;
    @Bind(R.id.btnSaveLapStopwatch)
    Button btnSaveLapStopwatch;
    @Bind(R.id.rvListStopwatch)
    RecyclerView rvListStopwatch;

    private OnFragmentInteractionListener mListener;

    public StopwatchFragment() {

    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStartStopwatch:
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    btnResetStopwatch.setEnabled(false);
                    btnStartStopwatch.setEnabled(false);
                    break;
                case R.id.btnPauseStopwatch:
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    btnResetStopwatch.setEnabled(true);
                    break;
                case R.id.btnResetStopwatch:
                    MillisecondTime = 0L;
                    StartTime = 0L;
                    TimeBuff = 0L;
                    UpdateTime = 0L;
                    Seconds = 0;
                    Minutes = 0;
                    MilliSeconds = 0;
                    tvStopwatch.setText("00:00:00");
                    btnStartStopwatch.setEnabled(true);
                    rvListStopwatch.setAdapter(null);
                    break;
                case R.id.btnSaveLapStopwatch:
                    listStopwatch.add(tvStopwatch.getText().toString());
                    adapter = new StopwatchAdapter(listStopwatch, context);
                    rvListStopwatch.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new Handler() ;

        v = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        ButterKnife.bind(this,v);
        rvListStopwatch.setLayoutManager(new LinearLayoutManager(context));

        btnStartStopwatch.setOnClickListener(option);
        btnPauseStopwatch.setOnClickListener(option);
        btnResetStopwatch.setOnClickListener(option);
        btnSaveLapStopwatch.setOnClickListener(option);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 100);

            tvStopwatch.setText("" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds));

            handler.postDelayed(this, 50);
        }

    };
}
