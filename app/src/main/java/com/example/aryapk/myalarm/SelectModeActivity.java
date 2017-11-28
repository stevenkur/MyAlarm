package com.example.aryapk.myalarm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectModeActivity extends AppCompatActivity {

    @Bind(R.id.btnAlarm) Button btnAlarm;
    @Bind(R.id.btnReminder) Button btnReminder;
    Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);
        Context = this;
        ButterKnife.bind(this);
        btnAlarm.setOnClickListener(Option);
        btnReminder.setOnClickListener(Option);
    }

    View.OnClickListener Option = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent Intent = null;
            switch (v.getId()){
                case R.id.btnAlarm:
                    Intent = new Intent(Context,HomeAlarmActivity.class);
                    startActivity(Intent);
                    break;
                case R.id.btnReminder:
                    Intent = new Intent(Context,HomeReminderActivity.class);
                    startActivity(Intent);
                    break;
            }
            finish();
        }
    };
}
