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
import com.example.aryapk.myalarm.adapters.StopwatchModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StopwatchFragment extends Fragment {

    long MillisecondTime = 0L, StartTime ;

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
    @Bind(R.id.btnResumeStopwatch)
    Button btnResumeStopwatch;
    @Bind(R.id.rvListStopwatch)
    RecyclerView rvListStopwatch;

    StopwatchModel stopwatchModel = new StopwatchModel();

    private OnFragmentInteractionListener mListener;

    public StopwatchFragment() {

    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStartStopwatch:
                    StartTime = SystemClock.uptimeMillis();
//                    handler.postDelayed(runnable, 0);
//                    btnStartStopwatch.setVisibility(View.GONE);
//                    btnPauseStopwatch.setVisibility(View.VISIBLE);
//                    btnSaveLapStopwatch.setVisibility(View.VISIBLE);
                    StopwatchStartState stopwatchStartState = new StopwatchStartState();
//                    runnable = stopwatchStartState.doAction(runnable, handler, btnStartStopwatch, btnPauseStopwatch, btnResetStopwatch, btnResumeStopwatch, btnSaveLapStopwatch);
                    runnable = stopwatchStartState.doAction(stopwatchModel);
                    stopwatchModel.setRunnable(runnable);
                    break;
                case R.id.btnPauseStopwatch:
//                    handler.removeCallbacks(runnable);
//                    btnPauseStopwatch.setVisibility(View.GONE);
//                    btnResumeStopwatch.setVisibility(View.VISIBLE);
//                    btnResetStopwatch.setVisibility(View.VISIBLE);
//                    btnSaveLapStopwatch.setVisibility(View.GONE);
                    StopwatchPauseState stopwatchPauseState = new StopwatchPauseState();
                    runnable = stopwatchPauseState.doAction(stopwatchModel);
                    stopwatchModel.setRunnable(runnable);
                    break;
                case R.id.btnResumeStopwatch:
//                    handler.postDelayed(runnable, 0);
//                    btnResumeStopwatch.setVisibility(View.GONE);
//                    btnResetStopwatch.setVisibility(View.GONE);
//                    btnPauseStopwatch.setVisibility(View.VISIBLE);
//                    btnSaveLapStopwatch.setVisibility(View.VISIBLE);
                    StopwatchResumeState stopwatchResumeState = new StopwatchResumeState();
                    runnable = stopwatchResumeState.doAction(stopwatchModel);
                    stopwatchModel.setRunnable(runnable);
                    break;
                case R.id.btnResetStopwatch:
                    MillisecondTime = 0L;
                    StartTime = 0L;
                    Seconds = 0;
                    Minutes = 0;
                    MilliSeconds = 0;
                    tvStopwatch.setText("00:00:00");
                    btnResumeStopwatch.setVisibility(View.GONE);
                    btnStartStopwatch.setVisibility(View.VISIBLE);
                    btnResetStopwatch.setVisibility(View.GONE);
                    btnPauseStopwatch.setVisibility(View.GONE);
                    btnSaveLapStopwatch.setVisibility(View.GONE);
                    listStopwatch.removeAll(listStopwatch);
                    if (adapter!=null)adapter.notifyDataSetChanged();
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
        btnResumeStopwatch.setOnClickListener(option);

        btnResetStopwatch.setVisibility(View.GONE);
        btnPauseStopwatch.setVisibility(View.GONE);
        btnSaveLapStopwatch.setVisibility(View.GONE);
        btnResumeStopwatch.setVisibility(View.GONE);

        stopwatchModel.setRunnable(runnable);
        stopwatchModel.setHandler(handler);
        stopwatchModel.setBtnStartStopwatch(btnStartStopwatch);
        stopwatchModel.setBtnPauseStopwatch(btnPauseStopwatch);
        stopwatchModel.setBtnResumeStopwatch(btnResumeStopwatch);
        stopwatchModel.setBtnResetStopwatch(btnResetStopwatch);
        stopwatchModel.setBtnSaveLapStopwatch(btnSaveLapStopwatch);

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

            Seconds = (int) (MillisecondTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (MillisecondTime % 1000 / 10);

            tvStopwatch.setText("" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%02d", MilliSeconds));

            handler.postDelayed(this, 50);
        }

    };
}
