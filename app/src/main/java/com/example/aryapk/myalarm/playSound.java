package com.example.aryapk.myalarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Aryapk on 12/6/2017.
 */

public class playSound extends AsyncTask<AlarmTurnOn, Void, Void> {

    public Context mContext;
    private AlarmTurnOn alarmToSet;
    public playSound (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(AlarmTurnOn... AlarmTurnOns) {
        alarmToSet = AlarmTurnOns[0];
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        String path = alarmToSet.getSoundPath() + ".mp3";
        Log.i("path 2",path);
        final Uri songUri = Uri.parse(path);
        new CountDownTimer(alarmToSet.getTimeCount(), 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Log.i("CountDown","finished");
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(mContext, songUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        }.start();
    }
}
