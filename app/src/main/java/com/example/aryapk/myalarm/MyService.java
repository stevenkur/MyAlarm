package com.example.aryapk.myalarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by Andy on 12/16/2017.
 */

public class MyService extends Service {
    private MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String extra = intent.getStringExtra("extra");
        Uri pathMusic = Uri.parse(extra);
        if(extra.equals("default"))
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        else
            player = MediaPlayer.create(this, pathMusic);
        player.setLooping(true);
        player.start();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
