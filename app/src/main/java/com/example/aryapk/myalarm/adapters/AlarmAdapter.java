package com.example.aryapk.myalarm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aryapk.myalarm.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Aryapk on 11/12/2017.
 */

public class AlarmAdapter extends BaseRecyclerViewAdapter {
    Context context;
    public AlarmAdapter(ArrayList<AlarmOverviewModel> items, Context context) {
        super(items);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm_preview, parent, false);
        context = parent.getContext();
        return new AlarmOverviewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AlarmOverviewHolder aoh = (AlarmOverviewHolder) holder;
        final AlarmOverviewModel modelAlarm = (AlarmOverviewModel) items.get(position);
        if (Integer.valueOf(modelAlarm.getHour())<10){
            aoh.tvAlarmHour.setText("0"+modelAlarm.getHour());
        }
        else {
            aoh.tvAlarmHour.setText(modelAlarm.getHour());
        }
        if (Integer.valueOf(modelAlarm.getMinute())<10){
            aoh.tvAlarmMinute.setText("0"+modelAlarm.getMinute());
        }
        else {
            aoh.tvAlarmMinute.setText(modelAlarm.getMinute());
        }
        aoh.tvAlarmName.setText(modelAlarm.getName());
        aoh.tvAlarmDate.setText(modelAlarm.getDate());
        aoh.tvAlarmStatus.setText(modelAlarm.getStatus());
        aoh.tvAlarmStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelAlarm.getStatus() == "Active"){
                    modelAlarm.setStatus("Inactive");
                }
                else {
                    /*modelAlarm.setCountDown(modelAlarm.createCountDown(context).start());
                    modelAlarm.setStatus("Active");*/
                }
            }
        });
        aoh.llAlarmItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static class AlarmOverviewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvAlarmHour)
        TextView tvAlarmHour;
        @Bind(R.id.tvAlarmMinute)
        TextView tvAlarmMinute;
        @Bind(R.id.tvAlarmTimeZone)
        TextView tvAlarmTimeZone;
        @Bind(R.id.tvAlarmDate)
        TextView tvAlarmDate;
        @Bind(R.id.tvAlarmName)
        TextView tvAlarmName;
        @Bind(R.id.tvAlarmStatus)
        TextView tvAlarmStatus;
        @Bind(R.id.llAlarmItem)
        LinearLayout llAlarmItem;

        public AlarmOverviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
