package com.example.satyadev.notes;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

/**
 * Created by satyadev on 17-09-2015.
 */
public class AlarmManagerBroadcast2   extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    SQLiteDatabase sql;
    TextView nameview, passview;
    Cursor cursor;
    EditText et, note;
    String name,pass1, pass = null;
    data data;
    View v;
    int id,year,day,momth,hour,minute;
    String time;

    private static final String BOOT_COMPLETED =
            "android.intent.action.BOOT_COMPLETED";
    private static final String QUICKBOOT_POWERON =
            "android.intent.action.QUICKBOOT_POWERON";

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            // Toast.makeText(this, "settled date :" + day + "/ " + (momth + 1) + " /" + year, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "beautiful", Toast.LENGTH_LONG).show();
            Intent serviceIntent = new Intent(context, boot.class);
            context.startService(serviceIntent);
        }

     /*  if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {

            Intent serviceIntent = new Intent(context, boot.class);
            context.startService(serviceIntent);
        }*/


    }
}
