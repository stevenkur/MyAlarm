package com.example.aryapk.myalarm.game;

/**
 * Created by Andy on 12/18/2017.
 */

public interface Observable {
    void subscribe(Observer obs);
    void unsubscribe(Observer obs);
    void sendNotification();
}
