package com.example.aryapk.myalarm.DBAlarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aryapk on 12/6/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{
    SQLiteDatabase dba;

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB","IsCreated");
        this.dba = db;
        String createQuery="CREATE TABLE IF NOT EXISTS alarm"+"(_id integer primary key autoincrement,"+"hour TEXT, minute TEXT, side TEXT, date TEXT, status TEXT, countdown INTEGER, name TEXT);";
        dba.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
