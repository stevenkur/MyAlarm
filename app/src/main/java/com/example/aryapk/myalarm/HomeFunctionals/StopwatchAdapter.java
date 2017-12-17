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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kurniawan on 17/12/2017.
 */

public class StopwatchAdapter extends BaseRecyclerViewAdapter {
    Context context;
    public StopwatchAdapter(ArrayList<String> items, Context context) {
        super(items);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stopwatch, parent, false);
        context = parent.getContext();
        return new StopwatchOverviewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final StopwatchOverviewHolder soh = (StopwatchOverviewHolder) holder;
        final String stopwatchItem = items.get(position).toString();
        soh.tvStopwatchItem.setText(stopwatchItem);
        soh.tvStopwatchId.setText(String.valueOf(position+1));
    }

    public static class StopwatchOverviewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvStopwatchItem)
        TextView tvStopwatchItem;
        @Bind(R.id.tvStopwatchId)
        TextView tvStopwatchId;

        public StopwatchOverviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
