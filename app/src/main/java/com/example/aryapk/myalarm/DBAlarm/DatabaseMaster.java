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
        databaseOpenHelper = new DatabaseOpenHelper(context,DB_NAME,null,1);
        Log.i("Master","isThere");
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

    public void insertData(String hour,String minute,String side,String date,String status,Integer countDown,String name){
        ContentValues newAlarm = new ContentValues();

        newAlarm.put("hour",hour);
        newAlarm.put("minute",minute);
        newAlarm.put("side",side);
        newAlarm.put("date",date);
        newAlarm.put("status",status);
        newAlarm.put("countDown",countDown);
        newAlarm.put("name",name);

        open();
        database.insert("alarm",null,newAlarm);
        close();
    }

    public void selectData(){
        /*cursor = NewAlarmActivity.rawQuery("SELECT * FROM alarm WHERE name = '" +
                getIntent().getStringExtra("name") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            hour.setText(cursor.getString(0).toString());
            minute.setText(cursor.getString(1).toString());
            side.setText(cursor.getString(2).toString());
            date.setText(cursor.getString(3).toString());
            status.setText(cursor.getString(4).toString());
            countDown.setText(cursor.getString(4).toString());
            name.setText(cursor.getString(4).toString());
        }

        btnSaveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });*/
    }

    public Cursor selectAll(){
        open();
        read();
        return database.query("alarm",new String[]{"_id","hour","minute","side","date","status","countDown","name"},null,null,null,null,"date");
    }
}
