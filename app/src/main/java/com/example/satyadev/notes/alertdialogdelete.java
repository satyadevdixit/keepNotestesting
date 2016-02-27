package com.example.satyadev.notes;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.satay.dev.example.notes.R;

/**
 * Created by satyadev on 02-10-2015.
 */

/** This file is used to custom design  alertdialog which pop-up when note is delete to confirm */
public class alertdialogdelete extends DialogFragment {



    LayoutInflater inflater;
    View v;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        final String myValue = mArgs.getString("name");

/** variable myvalue store the value of alarm id click by the user to delete the note */
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.alertdialogdelete,null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(v).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SQLiteDatabase sql;
                data database1 = new data(getActivity());

                sql = database1.getReadableDatabase();
                sql = database1.getWritableDatabase();
/** delete note from tablename 'tablename' */
                database1.delete(sql, myValue);

                Cursor cursor1 = database1.getdetaildataalarm(sql, myValue);
                if (cursor1.moveToFirst()) {

                    do {

/** Variable id and time store the id of that note and also id of alarms respectively. */

                        String time = cursor1.getString(1);

                        int canceltime = Integer.parseInt(time);


/** This method is called to cancel that alarm */
                        cancelRepeatingTimer(canceltime);

/** This method is used to delete alarm entry from table 'tablename3' where time is the id of alarm */
                        database1.deletenotificationalarm(sql,time);

                    }
                    while(cursor1.moveToNext());

                }


              //  Intent intent = new Intent(getActivity(), MainActivity.class);
               // startActivity(intent);



            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return alertDialog.create();
    }

    public void cancelRepeatingTimer(int name){
        AlarmManagerBroadcast alarm = new AlarmManagerBroadcast();
        Context context = this.getActivity() ;
        if(alarm != null){
            alarm.CancelAlarm(context,name);
        }
        else{

        }
    }

}


