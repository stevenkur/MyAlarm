package com.example.aryapk.myalarm.HomeFunctionals;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aryapk.myalarm.BaseRecyclerViewAdapter;
import com.example.aryapk.myalarm.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aryapk on 11/12/2017.
 */

public class HomeAlarmAdapter extends BaseRecyclerViewAdapter {
    Context context;
    public HomeAlarmAdapter(ArrayList<AlarmOverviewModel> items, Context context) {
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
        aoh.tvAlarmHour.setText(modelAlarm.getHour());
        aoh.tvAlarmMinute.setText(modelAlarm.getMinute());
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
