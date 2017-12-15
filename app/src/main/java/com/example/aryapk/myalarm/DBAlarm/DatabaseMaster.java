package com.example.aryapk.myalarm.DBAlarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Aryapk on 12/6/2017.
 */

public class DatabaseMaster {
    private static final String DB_NAME = "alarm.db";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

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
        /*cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());
            text5.setText(cursor.getString(4).toString());
        }
        ton2 = (Button) findViewById(R.id.button1);
        ton2.setOnClickListener(new View.OnClickListener() {

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
