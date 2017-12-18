package com.example.aryapk.myalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aryapk.myalarm.game.GameActivity;
import com.example.aryapk.myalarm.adapters.AlarmOverviewModel;
import com.example.aryapk.myalarm.game.GameNotification;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.*;

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

    private String hour, minute, side, date, status, name, path=" ";
    SharedPreferences sharedpreferences;
    AlarmOverviewModel model = new AlarmOverviewModel();
    GameNotification gameNotification = new GameNotification();
    GameActivity gameActivity = new GameActivity(this);

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
        if(getIntent().getSerializableExtra("modelAlarm")!=null){
            AlarmOverviewModel model = (AlarmOverviewModel) getIntent().getSerializableExtra("modelAlarm");
            tvName.setText(model.getName());
            path = model.getPath();
            String[] namaFile = path.split("/");
            tvTone.setText(namaFile[namaFile.length-1].substring(0,namaFile[namaFile.length-1].lastIndexOf('.')));
        }

    }

    private void insertAlarm(){
        hour = String.valueOf(tpClock.getCurrentHour());
        minute = String.valueOf(tpClock.getCurrentMinute());
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(currentTime);
        status = "Active";
        name = tvName.getText().toString();
        if (path==" ")path = FilePath.getPath(this, filePath);
        model.setDate(date);
        Log.i("CurDate",date);
        model.setHour(hour);
        model.setMinute(minute);
        model.setName(name);
        model.setPath(path);
        model.setStatus(status);
        setAlarm(path);
        gameNotification.subscribe(gameActivity);
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
        turnOn(path,duration).start();
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
        String[] paths = FilePath.getPath(this, filePath).split("/");
        Log.i("Selected File Name",paths[paths.length-1]);
        /*String[] fileName = paths[paths.length-1].split(".");*/
        tvTone.setText(paths[paths.length-1].substring(0,paths[paths.length-1].lastIndexOf('.')));
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
                gameNotification.addNewAlarm(model);
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
