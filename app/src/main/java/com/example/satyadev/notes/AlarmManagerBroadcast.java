package com.example.satyadev.notes;

/**
 * Created by satyadev on 04-09-2015.
 */
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.satay.dev.example.notes.R;

public class AlarmManagerBroadcast  extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    SQLiteDatabase sql;
    TextView nameview, passview;
    Cursor cursor;
    EditText et, note;
    String name,pass1, pass = null;
    int alarmid1;
    data data;
    Intent intent;
    View v;
    int id,year,day,momth,hour,minute;
    String time,title,description;
    private  byte[] img = null;
    int videocount;
    String deletetime,video;
    Bitmap resizedBitmap;
    Bitmap b1;
    private static final String BOOT_COMPLETED =
            "android.intent.action.BOOT_COMPLETED";
    private static final String QUICKBOOT_POWERON =
            "android.intent.action.QUICKBOOT_POWERON";

    @Override
    public void onReceive(Context context, Intent intent) {




       /* String action = intent.getAction();
        if (action.equals(BOOT_COMPLETED) ||
                action.equals(QUICKBOOT_POWERON)) {
           // Intent service = new Intent(context, BootService.class);
            Log.e("boot","scan start");
            context.startService(service);
        }*/

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();




        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();

        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
            //Make sure this intent has been sent by the one-time timer button.
            data = new data(context);
            sql = data.getWritableDatabase();
            sql = data.getReadableDatabase();

            Calendar calendar2 = Calendar.getInstance();
            int year1 = calendar2.get(Calendar.YEAR);
            int day1 = calendar2.get(Calendar.DAY_OF_MONTH);
            int momth1 = calendar2.get(Calendar.MONTH);
            int hour1 = calendar2.get(Calendar.HOUR_OF_DAY);
            int minute1 = calendar2.get(Calendar.MINUTE);


            GregorianCalendar calendar1 = new GregorianCalendar(year1, momth1, day1, hour1, minute1);
            long alarmtime = calendar1.getTimeInMillis();


            Cursor cursor1 = data.getnotificationdata(sql, alarmtime);

            if (cursor1!=null) {

                if (cursor1.moveToFirst()) {

                    do {
                        long wait1 = 10000;
                        // wait(wait1);

                        String id = cursor1.getString(0);
                        time = cursor1.getString(1);
                        String date = cursor1.getString(2);
                        String datetime = cursor1.getString(3);
                        String alarmid = cursor1.getString(4);


                        data.deletenotificationalarm(sql, alarmid);
                        data.setnotificationdataalarm(id, sql, date, time, datetime, alarmid);


                        String time1 = "1";

                        data.upadatenotificationumber(sql, id, time1, alarmid);


                        Cursor cursor4 = data.getnotificationdetaildata1(sql, id);


/** variable name , pass and time1 store title, message and indicator that notification is issue for
 that note */
                        if (cursor4.moveToFirst()) {

                            title = cursor4.getString(0);
                            description = cursor4.getString(1);
                            video = cursor4.getString(5);
                            if (video!=null) {
                                Log.e("broadcast","rece"+video);
                                videocount = Integer.parseInt(video);
                            }
                            img = cursor4.getBlob(cursor4.getColumnIndex("image"));
                            if (img != null) {
                                b1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                                Display display = wm.getDefaultDisplay();
                                int  height = display.getHeight();
                                int  width = display.getWidth();
                                Log.e("height","of phone"+height);
                                Log.e("width","of phone"+width);
                                if (height>width) {
                                    int newwidth = (int) (width / 4);
                                    width = newwidth;
                                    height = newwidth;
                                }
                                else if (width>height)
                                {
                                    int newwidth = (int) (height / 5);
                                    width = newwidth;
                                    height = newwidth;
                                }
                                Log.e("height","of device"+height);
                                Log.e("width","of device"+width);
                                float mbtotal = megabytesAvailable();


                                resizedBitmap = b1.createScaledBitmap(b1, (int) (width), (int) (height), false);


                            }
                            else if (videocount==1)
                            {
                                img = cursor4.getBlob(cursor4.getColumnIndex("preview"));
                                b1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                                Display display = wm.getDefaultDisplay();
                                int  height = display.getHeight();
                                int  width = display.getWidth();
                                Log.e("height","of phone"+height);
                                Log.e("width","of phone"+width);
                                if (height>width) {
                                    int newwidth = (int) (width / 4);
                                    width = newwidth;
                                    height = newwidth;
                                }
                                else if (width>height)
                                {
                                    int newwidth = (int) (height / 5);
                                    width = newwidth;
                                    height = newwidth;
                                }
                                Log.e("height","of device"+height);
                                Log.e("width","of device"+width);
                                float mbtotal = megabytesAvailable();


                                resizedBitmap = b1.createScaledBitmap(b1, (int) (width), (int) (height), false);

                            }



                        }

                        Cursor cursor3 = data.getnotificationdataalarm(sql);
                        int rowcount = cursor3.getCount();
                        name = Integer.toString(rowcount);

                        Notification(context, name, title, alarmid, id,videocount);

                    }
                    while (cursor1.moveToNext());

                }


            }


           // msgStr.append("One time Timer : ");



        }

        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        msgStr.append(formatter.format(new Date()));

      //  Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

        //Release the lock
        wl.release();
    }



    public void Notification(Context context, String message,String title,String alarmid,String id,int videocounter) {
        // Set Notification Title
        long when = System.currentTimeMillis();
        ImageView imageView;


//Log.e("titlw","is="+title);
String title1 = title + "shbhdcbbdhbjdbjjdkjkwsduisdnksjdjdsajdkljasdj    dnnqdjiwddjqoiqjsdqwkdwjdi   sjdjkjdjwdjwj";
        if (videocounter!=1) {
            String check = "notification";
             alarmid1 = Integer.parseInt(alarmid);
             intent = new Intent(context, details.class);
            intent.putExtra("id", String.valueOf(id));
            intent.putExtra("id2", check);
        }
        else if (videocounter==1)
        {
            String check = "notification";
            alarmid1 = Integer.parseInt(alarmid);
             intent = new Intent(context, showfetchvideo.class);
            intent.putExtra("id", String.valueOf(id));
            intent.putExtra("id2", check);
        }
        Log.e("resize","bitmap"+resizedBitmap);
        final int _id = (int) System.currentTimeMillis();
        PendingIntent pIntent = PendingIntent.getActivity(context, _id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

      //  Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.motificatioicon );

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)

                // Set Icon
                .setSmallIcon(R.mipmap.notificationicon)
                .setLargeIcon(resizedBitmap)
                        // Set Ticker Message
                .setTicker(title)
                        // Set Title
                .setContentTitle("KeepNotes")
                        // Set Text
                .setContentText("" + title)

                        // Add an Action Button below Notification
              //  .addAction(R.drawable.newcalender1, "Action Button", pIntent)
                        // Set PendingIntent into Notification
                .setContentIntent(pIntent)
               // .setWhen(when)

                .setSound(alarmSound)
        //The vibration now has a delay of 1000 ms. If you set the
        //first one to 0, it will go off instantly. It's a { delay, vibrate, sleep, vibrate, sleep } pattern

                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
        // Dismiss Notification
                .setAutoCancel(true);


        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(alarmid1, builder.build());

    }

    // Check for network availability

    public float megabytesAvailable() {


        final float BYTES_IN_MB = 1024.0f * 1024.0f;
        float bytesAvailable = 0;
        try {

            Runtime rt = Runtime.getRuntime();
            bytesAvailable = rt.maxMemory();
            return bytesAvailable / BYTES_IN_MB;
        }
        catch (Exception u)
        {

        }
        return  bytesAvailable;
    }






    public void SetAlarm(Context context)
    {
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        int t = 5000;
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), t, pi);
        //Log.e("good one", "set alaram is done");
    }



    public void CancelAlarm(Context context,int name)
    {
      //  long x = Long.parseLong(name);
       // int x1 = (int)  x;
       // long hh=9019090;
        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,name, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);


        Log.e("hhhhh ", "alarm done" + name);
    }

    public void setOnetimeTimer(Context context,long settime,int id){


        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
       // PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
      // final int id =(int) System.currentTimeMillis();

      //  String res=Integer.toString(id);
        Log.e("final", "int" + id);

        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent,0);

       // am.set(AlarmManager.RTC_WAKEUP, settime, pi);




        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, settime, pi);
           // Toast.makeText(context, " reminder set", Toast.LENGTH_LONG).show();
        }

        else {
            am.set(AlarmManager.RTC_WAKEUP, settime, pi);
            //Toast.makeText(context, " reminder set", Toast.LENGTH_LONG).show();
        }

    }


   /* @SuppressLint("NewApi")
    public  void setOnetimeTimer(Context context, long settime, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcast.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent,0);
        intent.putExtra(ONE_TIME, Boolean.TRUE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, settime, pi);
            Toast.makeText(context, " kitkat 19", Toast.LENGTH_LONG).show();
        }

        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, settime, pi);
            Toast.makeText(context, " not jelly bean", Toast.LENGTH_LONG).show();
        }
    }*/

}

