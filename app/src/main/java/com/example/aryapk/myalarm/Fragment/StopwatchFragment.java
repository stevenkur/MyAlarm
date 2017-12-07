package com.example.aryapk.myalarm.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aryapk.myalarm.HomeAlarmActivity;
import com.example.aryapk.myalarm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StopwatchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StopwatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopwatchFragment extends Fragment {

    HomeAlarmActivity activity;
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

    private OnFragmentInteractionListener mListener;

    public StopwatchFragment() {

    }

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStartStopwatch:
                case R.id.btnPauseStopwatch:
                case R.id.btnResetStopwatch:
                case R.id.btnSaveLapStopwatch:
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
        v =  inflater.inflate(R.layout.fragment_stopwatch, container, false);
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
}
