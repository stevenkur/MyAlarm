package com.example.aryapk.myalarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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

import com.example.aryapk.myalarm.Game.GameActivity;
import com.example.aryapk.myalarm.HomeFunctionals.AlarmOverviewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewAlarmActivity extends AppCompatActivity {
    @Bind(R.id.btnSaveAlarm)
    Button btnSaveAlarm;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.tvTone)
    TextView tvTone;
    @Bind(R.id.tpClock)
    TimePicker tpClock;
    @Bind(R.id.tvName)
    TextView tvName;

    private String hour, minute, side, date, status, name, path;
    SharedPreferences sharedpreferences;
    AlarmOverviewModel model = new AlarmOverviewModel();

    Context activity;
    private Uri filePath;
    private int PICK_SOUND_REQUEST = 1;
    private AlarmManager alarmManager;
    ArrayList<AlarmOverviewModel> alarmList = new ArrayList<>();
    private PendingIntent pendingIntent;

    View.OnClickListener option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.btnSaveAlarm:
                    insertAlarm();
                    i = new Intent(activity, AlarmHomeActivity.class);
                    startActivity(i);
                    break;
                case R.id.btnCancel:
                    i = new Intent(activity, AlarmHomeActivity.class);
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
        btnSaveAlarm.setOnClickListener(option);
        btnCancel.setOnClickListener(option);
        tvTone.setOnClickListener(options);
        tvName.setOnClickListener(options);
    }

    private void insertAlarm(){
        hour = String.valueOf(tpClock.getCurrentHour());
        minute = String.valueOf(tpClock.getCurrentMinute());
        Date currentTime = Calendar.getInstance().getTime();
        date = String.valueOf(currentTime.getDay())+"/"+String.valueOf(currentTime.getMonth())+"/"+String.valueOf(currentTime.getYear());
        status = "Active";
        name = tvName.getText().toString();
        path = FilePath.getPath(this, filePath);
        model.setDate(date);
        model.setHour(hour);
        model.setMinute(minute);
        model.setName(name);
        model.setPath(path);
        model.setStatus(status);
        setAlarm(path);
    }

    private void createSharedPreference(){
        alarmList.add(model);
        SharedPreferences mySharedPreferences = getSharedPreferences("alarmDB",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = mySharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarmList);
        edt.putString("alarmList",json);
        edt.commit();
    }

    private void getPreferenceListAlarm(){
        alarmList.removeAll(alarmList);
        SharedPreferences mySharedPreferences = getSharedPreferences("alarmDB",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mySharedPreferences.getString("alarmList","Empty");
        Type type = new TypeToken<ArrayList<AlarmOverviewModel>>(){}.getType();
        if (json != "Empty"){
            alarmList = gson.fromJson(json,type);
            Log.i("AlarmList",alarmList.toString());
            createSharedPreference();
            Log.i("List","notEmpty");
        }
        else {
            Log.i("List","Empty");
            createSharedPreference();
        }
    }

    private void setAlarm(String path){
        Date currentTime = Calendar.getInstance().getTime();
        long currentHour=currentTime.getHours();
        long currentMinute=currentTime.getMinutes();
        long currentSecond=currentTime.getSeconds();
        long selectedHour = tpClock.getCurrentHour();
        long selectedMinute = tpClock.getCurrentMinute();
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

        turnOn(path,duration);
        getPreferenceListAlarm();
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

    private CountDownTimer turnOn(String paths, long duration){
        String path = paths;
        Log.i("path 2",path);
        final Uri songUri = Uri.parse(path);
        long toSum = duration;
        Log.i("Duration", String.valueOf(duration));
        long hourTotal = toSum/3600000;
        toSum = toSum - hourTotal*3600000;
        long minuteTotal = toSum/60000;
        Toast.makeText(this,"Alarm set for "+String.valueOf(hourTotal)+" hours and "+String.valueOf(minuteTotal)+" minutes from now",Toast.LENGTH_LONG).show();
        CountDownTimer countDownTimer =
        new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                Intent i = new Intent(activity,GameActivity.class);
                startActivity(i);
            }
        };
        return  countDownTimer;
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
        Intent i = new Intent(activity, AlarmHomeActivity.class);
        startActivity(i);
        finish();
    }


}
