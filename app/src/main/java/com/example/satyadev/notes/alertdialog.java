package com.example.satyadev.notes;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.satay.dev.example.notes.R;

/**
 * Created by satyadev on 02-10-2015.
 */


/** This file is used to custom design alarm alertdialog */
public class alertdialog extends DialogFragment {



LayoutInflater inflater;
    View v;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();

/** variable myvalue store the value of alarm id click by the user to delete the alarm */
        final String myValue = mArgs.getString("name");
        inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.alertdialog,null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setView(v).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int alarmid = Integer.parseInt(myValue);

                SQLiteDatabase sql;
                data database1 = new data(getActivity());

                sql = database1.getWritableDatabase();
/** delete alarm from tablename 'table3'*/
                database1.deletenotificationalarm(sql, myValue);

/** cancel alarm of id alarmid */
                cancelRepeatingTimer(alarmid);

             //   Intent intent = new Intent(getActivity(), alarmmanager.class);
              //  startActivity(intent);


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
