package com.example.aryapk.myalarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aryapk.myalarm.DBAlarm.DatabaseMaster;
import com.example.aryapk.myalarm.DBAlarm.DatabaseOpenHelper;
import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.aryapk.myalarm.R.id.time;
import static com.example.aryapk.myalarm.R.id.tvAlarmHour;
import static com.example.aryapk.myalarm.R.id.tvAlarmMinute;

public class NewAlarmActivity extends AppCompatActivity {
    @Bind(R.id.btnSaveAlarm)
    Button btnSaveAlarm;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.tvRepeat)
    TextView tvRepeat;
    @Bind(R.id.tvSnooze)
    TextView tvSnooze;
    @Bind(R.id.tvSound)
    TextView tvSound;
    @Bind(R.id.tvTone)
    TextView tvTone;
    @Bind(R.id.tpClock)
    TimePicker tpClock;
    @Bind(R.id.tvName)
    TextView tvName;

    private String hour, minute, side, date, status, name, path;
    private Integer countDown;

    Context activity;
    private Uri filePath;
    private int PICK_SOUND_REQUEST = 1;
    private DatabaseMaster dbMaster = new DatabaseMaster(NewAlarmActivity.this);
    ArrayList<AlarmOverviewModel> alarmList = new ArrayList<>();
    DatabaseOpenHelper dbOpenHelper;

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.btnSaveAlarm:
                    getSound();
                    insertAlarm();
                    Toast.makeText(getApplicationContext(), "Berhasil Tambah Alarm", Toast.LENGTH_LONG).show();
                    i = new Intent(activity, HomeAlarmActivity.class);
                    startActivity(i);
                    break;
                case R.id.btnCancel:
                    i = new Intent(activity, HomeAlarmActivity.class);
                    startActivity(i);
                    break;
            }
            finish();
        }
    };

    View.OnClickListener options = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tvTone:
                    showFileChooser();
                    break;
                case R.id.tvName:
                    createDialog();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        ButterKnife.bind(this);
        activity = this;
        showFileChooser();
        btnSaveAlarm.setOnClickListener(option);
        btnCancel.setOnClickListener(option);
        tvSound.setOnClickListener(options);
        tvName.setOnClickListener(options);
    }

    private void insertAlarm(){
        hour = String.valueOf(tpClock.getHour());
        minute = String.valueOf(tpClock.getMinute());
        side = String.valueOf(side);
        date = String.valueOf(date);
        status = "ON";
        name = String.valueOf(name);
        path = FilePath.getPath(this, filePath);

        dbMaster.insertData(hour, minute, date, status, name, path);


    }

    private void getSound(){
        String path = FilePath.getPath(this, filePath);
        setAlarm(path);
    }

    private void setAlarm(String path){
        Date currentTime = Calendar.getInstance().getTime();
        long currentHour=currentTime.getHours();
        long currentMinute=currentTime.getMinutes();
        long currentSecond=currentTime.getSeconds();
        long selectedHour = tpClock.getHour();
        long selectedMinute = tpClock.getMinute();
        Log.i("Current Time",currentHour+ " : "+ currentMinute);
        Log.i("Selected Time",selectedHour+ " : "+ selectedMinute);
        if (currentHour>selectedHour){
            selectedHour+=24;
        }
        long currentSeconds = currentHour*3600 + currentMinute*60 + currentSecond;
        long currentMSecond = currentSeconds*1000;
        long selectedSeconds = selectedHour*3600 + selectedMinute*60;
        long selectedMSecond = selectedSeconds*1000;
        long duration = selectedMSecond-currentMSecond;
        long toSum = duration;
        Log.i("Duration", String.valueOf(duration));
        long hourTotal = toSum/3600000;
        toSum = toSum - hourTotal*3600000;
        long minuteTotal = toSum/60000;
        Toast.makeText(this,"Alarm set for "+String.valueOf(hourTotal)+" hours and "+String.valueOf(minuteTotal)+" minutes from now",Toast.LENGTH_LONG).show();
        /*AlarmTurnOn alarmTurnOn = new AlarmTurnOn();
        alarmTurnOn.setSoundPath(path);
        alarmTurnOn.setTimeCount(duration);
        Log.i("Path",path);
        new PlaySound(activity).execute(alarmTurnOn);*/

        turnOn(path,duration);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_SOUND_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_SOUND_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    private void turnOn(String paths, long duration){
        String path = paths;
        Log.i("path 2",path);
        final Uri songUri = Uri.parse(path);
        new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Log.i("CountDown","finished");
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(activity, songUri);
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

    public static class AlarmNameDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Alarm Name")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private void createDialog(){
        final EditText input = new EditText(NewAlarmActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewAlarmActivity.this);
        alertDialog.setTitle("Alarm Name");
        alertDialog.setView(input);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvName.setText(input.getText().toString());
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(activity, HomeAlarmActivity.class);
        startActivity(i);
        finish();
    }


}
