package com.example.satyadev.notes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by satyadev on 17-09-2015.
 */
public class boot  extends Service {

    SQLiteDatabase sql;
    private   AlarmManagerBroadcast alarmManagerBroadcast = new AlarmManagerBroadcast();
    data data;
    String date,time;
    Cursor cursor;

    @Override
    public void onCreate() {

        super.onCreate();
        Context context = this.getApplicationContext();
        // Set your alarm here as you do in "1. First I set an alarm in alarm manager"
        AlarmManagerBroadcast alarmManagerBroadcast = new AlarmManagerBroadcast();
        data = new data(boot.this);
        sql = data.getWritableDatabase();
        sql = data.getReadableDatabase();
        cursor = data.getnotificationdataalarmtable3(sql);

        if (cursor.moveToFirst()) {
            do {
                int t = 0;

              //  Toast.makeText(this, "beautiful", Toast.LENGTH_LONG).show();
                String year = cursor.getString(6);
                String month = cursor.getString(7);
                String day = cursor.getString(8);
                String hour = cursor.getString(9);
                String minute = cursor.getString(10);

                int year1 = Integer.parseInt(year);
                int month1 = Integer.parseInt(month);
                int day1 = Integer.parseInt(day);
                int hour1 = Integer.parseInt(hour);
                int minute1 = Integer.parseInt(minute);


                time = cursor.getString(4);
                GregorianCalendar calendar = new GregorianCalendar(year1, month1, day1, hour1, minute1);

                long x = Long.parseLong(time);
                String cancel = cursor.getString(5);
                int canceltime = Integer.parseInt(cancel);


                Calendar calendar2 = Calendar.getInstance();
                int year2 = calendar2.get(Calendar.YEAR);
                int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                int momth2 = calendar2.get(Calendar.MONTH);
                int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
                int minute2 = calendar2.get(Calendar.MINUTE);


                t = calendar.compareTo(calendar2);

                if (t == 1) {
                    if (alarmManagerBroadcast != null) {

                        alarmManagerBroadcast.setOnetimeTimer(context, x, canceltime);

                    }



                }
                else
                {
                    data.deletenotificationalarm(sql, cancel);
                }
            }
            while (cursor.moveToNext());

        }

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
       // Toast.makeText(this, "beautiful", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
