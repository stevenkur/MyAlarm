package com.example.aryapk.myalarm.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aryapk.myalarm.AlarmHomeActivity;
import com.example.aryapk.myalarm.HomeAlarmActivity;
import com.example.aryapk.myalarm.R;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StopwatchFragment extends Fragment {

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    String[] ListElements = new String[] {  };

    List<String> ListElementsArrayList ;

    ArrayAdapter<String> adapter ;

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
    @Bind(R.id.lvStopwatch)
    ListView lvStopwatch;

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
                case R.id.btnPauseStopwatch:
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    btnResetStopwatch.setEnabled(true);
                case R.id.btnResetStopwatch:
                    MillisecondTime = 0L;
                    StartTime = 0L;
                    TimeBuff = 0L;
                    UpdateTime = 0L;
                    Seconds = 0;
                    Minutes = 0;
                    MilliSeconds = 0;
                    tvStopwatch.setText("00:00:00");
                case R.id.btnSaveLapStopwatch:
                    ListElementsArrayList.add(tvStopwatch.getText().toString());
                    adapter.notifyDataSetChanged();
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

//        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
//
//        adapter = new ArrayAdapter<String>(StopwatchFragment.this,
//                android.R.layout.simple_list_item_1,
//                ListElementsArrayList
//        );
//
//        lvStopwatch.setAdapter(adapter);

        v = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        ButterKnife.bind(this,v);
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

            MilliSeconds = (int) (UpdateTime % 1000);

            tvStopwatch.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}
