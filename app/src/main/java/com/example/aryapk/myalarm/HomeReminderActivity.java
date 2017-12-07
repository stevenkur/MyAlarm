package com.example.aryapk.myalarm;

import android.content.Context;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeReminderActivity extends AppCompatActivity {

    @Bind(R.id.cvReminderCalendar) CalendarPickerView CalendarPickerView;
    Context Context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_reminder);
        ButterKnife.bind(this);
        Context = this;
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);
        Date today = new Date();
        CalendarPickerView.init(today, nextYear.getTime())
                .withSelectedDate(today);
        CalendarPickerView.setOnDateSelectedListener(Option);
    }

    com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener Option = new CalendarPickerView.OnDateSelectedListener() {
        @Override
        public void onDateSelected(Date date) {

        }

        @Override
        public void onDateUnselected(Date date) {
            Log.i("Touch",String.valueOf(CalendarPickerView.getSelectedDate()));
            Toast.makeText(Context,String.valueOf(CalendarPickerView.getSelectedDate()),Toast.LENGTH_LONG).show();
        }
    };


}
