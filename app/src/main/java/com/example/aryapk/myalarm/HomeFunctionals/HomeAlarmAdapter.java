package com.example.aryapk.myalarm.HomeFunctionals;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeAlarmAdapter extends BaseRecyclerViewAdapter{
    public HomeAlarmAdapter(ArrayList<AlarmOverviewModel> items){
        super(items);
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
        AlarmOverviewModel modelAlarm =(AlarmOverviewModel) items.get(position);
        aoh.tvAlarmHour.setText(modelAlarm.getHour());
        aoh.tvAlarmMinute.setText(modelAlarm.getMinute());
        aoh.tvAlarmTimeZone.setText(modelAlarm.getSide());
        aoh.tvAlarmDate.setText(modelAlarm.getDate());
        aoh.tvAlarmStatus.setText(modelAlarm.getStatus());
    }
    public static class AlarmOverviewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tvAlarmHour) TextView tvAlarmHour;
        @Bind(R.id.tvAlarmMinute) TextView tvAlarmMinute;
        @Bind(R.id.tvAlarmTimeZone) TextView tvAlarmTimeZone;
        @Bind(R.id.tvAlarmDate)TextView tvAlarmDate;
        @Bind(R.id.tvAlarmStatus)TextView tvAlarmStatus;
        public AlarmOverviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
