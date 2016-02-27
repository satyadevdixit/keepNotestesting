package com.example.satyadev.notes;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.satay.dev.example.notes.R;

/**
 * Created by satyadev on 31-12-2015.
 */
public class sharevideoalert extends DialogFragment {


    Uri uri;
    LayoutInflater inflater;
    View v;
    String title,note;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();


/** variable myvalue store the value of alarm id click by the user to delete the alarm */
        final String myValue = mArgs.getString("name");
        try {

            Log.e("show", "video" + myValue + "hjdhjewhd" );
            Intent intent1 = Intent.parseUri(myValue, 0);
            uri = intent1.getData();
            Log.e("show", "video" + uri + "hjdhjewhd" + intent1);
        } catch (Exception h) {
            Log.e("show fetch", "video error" + h);
        }
        inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.sharevideoalert,null);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setView(v).setPositiveButton("share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Dialog dialogView = dialog.
                        EditText editText = (EditText) getDialog().findViewById(R.id.title);
                EditText editText2 = (EditText) getDialog().findViewById(R.id.note);
              //  EditText editText= (EditText) dialogView.findViewById(R.id.title);
                title = editText.getText().toString();
                note = editText2.getText().toString();
                share();

                Intent intent = new Intent(getActivity(), alarmmanager.class);
                //  startActivity(intent);


            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return alertDialog.create();
    }


    public void share() {
       try {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
              shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
           shareIntent.putExtra(Intent.EXTRA_TEXT, note);
            shareIntent.setType("video/*");
       // shareIntent.setType("text/");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Log.e("location", "of file" + title+note);
            startActivity(Intent.createChooser(shareIntent, "Share Video"));

       }



        catch (Exception e)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "KeepNotes share"));


        }

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

