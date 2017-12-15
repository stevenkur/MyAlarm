package com.example.aryapk.myalarm.DBAlarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aryapk.myalarm.NewAlarmActivity;
import com.example.aryapk.myalarm.R;

import butterknife.Bind;

import static android.R.attr.countDown;
import static android.R.attr.name;

/**
 * Created by Aryapk on 12/6/2017.
 */

public class DatabaseMaster {
    private static final String DB_NAME = "alarm.db";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;
    @Bind(R.id.btnSaveAlarm)
    Button btnSaveAlarm;

    public DatabaseMaster(Context context){
        databaseOpenHelper = new DatabaseOpenHelper(context, DB_NAME, null, 1);
        Log.i("Master", "isThere");
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void read() throws SQLException{
        database = databaseOpenHelper.getReadableDatabase();
    }

    public void close(){
        if (database!=null) database.close();
    }

    public void insertData(String hour, String minute, String date, String status, String name, String path){
        ContentValues newAlarm = new ContentValues();

        newAlarm.put("hour", hour);
        newAlarm.put("minute", minute);
        newAlarm.put("date", date);
        newAlarm.put("status", status);
        newAlarm.put("name", name);
        newAlarm.put("path", path);

        open();
        database.insert("alarm", null, newAlarm);
        close();
    }

    public void updateData(Integer id, String hour, String minute, String side, String date, String status, String name, String path){
        ContentValues editAlarm = new ContentValues();

        editAlarm.put("hour", hour);
        editAlarm.put("minute", minute);
        editAlarm.put("date", date);
        editAlarm.put("status", status);
        editAlarm.put("name", name);
        editAlarm.put("path", path);

        open();
        database.update("alarm", editAlarm, "_id="+id, null);
        close();
    }

    public void deleteData(String id){
        open();
        database.delete("alarm", "_id="+id, null);
        close();
    }

    public Cursor selectAll(){
        open();
        read();
        return database.query("alarm", new String[]{"_id", "hour", "minute", "side", "date", "status", "countDown", "name"}, null, null, null, null, "date");
    }
}
