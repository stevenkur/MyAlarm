package com.example.aryapk.myalarm;

import com.example.aryapk.myalarm.adapters.AlarmOverviewModel;

import java.util.ArrayList;

/**
 * Created by Aryapk on 11/13/2017.
 */

public class DummyUtils {
    private String[] dummyHour = {"01", "12", "04"};
    private String[] dummyMinute = {"30", "45", "00"};
    private String[] dummyDate = {"Mon, Nov 13", "Tue, Nov 7", "Sun, Nov 12"};

    public ArrayList<AlarmOverviewModel> dummyModel = new ArrayList<>();

    public ArrayList<AlarmOverviewModel> getDummyModel() {
        setDummyModel();
        return dummyModel;
    }

    private void setDummyModel() {
        for (int i = 0; i <= 2; i++) {
            AlarmOverviewModel item = new AlarmOverviewModel();
            item.setHour(dummyHour[i]);
            item.setMinute(dummyMinute[i]);
            item.setDate(dummyDate[i]);
            /*if (i % 2 == 0) {
                item.setSide("AM");
                item.setStatus("Active");
            } else {
                item.setSide("PM");
                item.setStatus("Inactive");
            }*/
            dummyModel.add(item);
        }
    }
}
