package com.example.aryapk.myalarm.game;

import com.example.aryapk.myalarm.adapters.AlarmOverviewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 12/18/2017.
 */

public class GameNotification implements Observable {

    private List<Observer<AlarmOverviewModel>> alarm;
    private AlarmOverviewModel newAlarm;

    public GameNotification() {
        alarm = new ArrayList<Observer<AlarmOverviewModel>>();
    }

    public void addNewAlarm(AlarmOverviewModel alarmOverviewModel) {
        newAlarm = alarmOverviewModel;
        sendNotification();
    }

    @Override
    public void subscribe(Observer obs) {
        alarm.add(obs);
    }

    @Override
    public void unsubscribe(Observer obs) {
        alarm.remove(obs);
    }

    @Override
    public void sendNotification() {
        for(Observer o : alarm){
            o.notify(newAlarm);
        }
    }
}
