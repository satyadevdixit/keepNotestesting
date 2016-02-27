package com.example.satyadev.notes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.satay.dev.example.notes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class showfetchvideo extends AppCompatActivity {

    Uri videoUri;
    private int RESULT_LOAD_VID = 4;
    private AlarmManagerBroadcast alarm;
    private int REQUEST_VIDEO_CAPTURE = 3;
    data database1;
    String intent,id,fetchintent,dateshow,newnumber,pass = null;
    EditText et, note;
    static final int dialog_id = 0;
    static final int dialog_date = 1;
    int hour, minute,x,timecounter = 0,datecounter = 0;
    int year, day, momth;
    SQLiteDatabase sql;
    private static byte[] img;
    ImageView imageview,deleteimage;
    Uri uri;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showfetchvideo);
        database1 = new data(showfetchvideo.this);
        id = getIntent().getStringExtra("id");
        sql = database1.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        momth = calendar.get(Calendar.MONTH);
        alarm = new AlarmManagerBroadcast();
        imageview = (ImageView) findViewById(R.id.imgView);
        imageview.setVisibility(View.GONE);
        deleteimage = (ImageView) findViewById(R.id.play);
        deleteimage.setVisibility(View.GONE);

/** same as mainactivity line 125. */
        sql = database1.getReadableDatabase();
        note = (EditText) findViewById(R.id.note);
        et = (EditText) findViewById(R.id.title);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Log.e("lkillln0;0n","video");
        showfetchvideo();
        //check = getIntent().getStringExtra("id2");
        setupToolbar();

    }




public void showfetchvideo()
{
try {

    Cursor cursor = database1.fetchvideo(sql, id);
    Log.e("show", "videoy o89o9 9" + cursor.getCount());
    if ((cursor.getCount()) > 0) {
        if (cursor.moveToFirst()) {
            String vid = cursor.getString(1);
            intent = cursor.getString(0);
            String title = cursor.getString(2);
            String notetext = cursor.getString(3);
            img = cursor.getBlob(cursor.getColumnIndex("preview"));
            if (title!=null) {
                et.setText(title, TextView.BufferType.EDITABLE);
                note.setText(notetext, TextView.BufferType.EDITABLE);
                note.setFocusable(true);
                note.setMovementMethod(LinkMovementMethod.getInstance());
            }
            Bitmap videopreview = BitmapFactory.decodeByteArray(img, 0, img.length);
            try {

                Log.e("show", "video" + intent + "hjdhjewhd" + vid);
                Intent intent1 = Intent.parseUri(intent, 0);
                uri = intent1.getData();
                Log.e("show", "video" + imageview + "hjdhjewhd" + img);
                imageview.setVisibility(View.VISIBLE);
                imageview.setImageBitmap(videopreview);
                deleteimage.setVisibility(View.VISIBLE);
                Log.e("show", "video" + uri + "" + img);
            } catch (Exception h) {
                Log.e("show fetch", "video error" + h);
            }
        }
    }
}
catch (Exception e)
{

}
}

    public void playvideo(View view) {

        Intent intent1 = new Intent(showfetchvideo.this,playvideo.class);
        intent1.putExtra("videouri",id);
        intent1.putExtra("activity", "showvideo");
        startActivity(intent1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {


        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){


        }
    }

    /** This method is used to show dialog date and time picker on screen if id equal dialog_id show time picker
     and if id equal to dialog_date then show date picker*/
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case dialog_id:
                return new TimePickerDialog(this, mtimesetlistener, hour, minute, false);
            case dialog_date:
                return new DatePickerDialog(this, mdatesetlistener, year, momth, day);
        }
        return null;
    }


    /** return datepicker data means the date choose by the user. */
    private DatePickerDialog.OnDateSetListener mdatesetlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {

            year = year1;
            momth = monthOfYear;
            day = dayOfMonth;
            datecounter =  1;
            dateshow = day + "/ " + (momth + 1) + " /" + year;


        }
    };


    /** return time picker data which is choose by the user.*/
    private TimePickerDialog.OnTimeSetListener mtimesetlistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int hour_minute) {

            hour = hourOfDay;
            minute = hour_minute;
            timecounter = 1;


        }
    };


    /** This method set alarm */
    public void setalarm(MenuItem item) {

        try {

            try {


/** These 2 variables datecounter and timecounter indicates that user choose date and time if both variables values is 1
 and if both variables value is not 1 then it means not select both date and  time */
                if ((datecounter == 1) && (timecounter == 1)) {
                    int t = 0;

/**  This line takes  the choose values  by the user in date and time picker */
                    GregorianCalendar calendar = new GregorianCalendar(year, momth, day, hour, minute);

/**  This line get the value from MONTHS array beacuse user choose month value is  in counting and by
 array get the month name in words */
                    String monthname = MONTHS[momth];

/**  In below 2 lines make the date and time  to store in database to show for the user at what time user
 set alarm **/
                    String date = monthname + " " + day + "," + " " + year;
                    String time = hour + " : " + minute;
                    //  Toast.makeText(getBaseContext(), "settled time :  " + hour + " : " + minute, Toast.LENGTH_LONG).show();
                    /** Converting the date and time in to milliseconds elapsed since epoch */
                    long alarm_time = calendar.getTimeInMillis();


/** In below 5 lines convert the integer values into string        **/
                    String year2 = Integer.toString(year);
                    String momth2 = Integer.toString(momth);
                    String day2 = Integer.toString(day);
                    String hour2 = Integer.toString(hour);
                    String minute2 = Integer.toString(minute);


/** Get the current time */
                    Calendar calendar2 = Calendar.getInstance();
                    int year1 = calendar2.get(Calendar.YEAR);
                    int day1 = calendar2.get(Calendar.DAY_OF_MONTH);
                    int momth1 = calendar2.get(Calendar.MONTH);
                    int hour1 = calendar2.get(Calendar.HOUR_OF_DAY);
                    int minute1 = calendar2.get(Calendar.MINUTE);

/** Again set them to store at what time the alarm is set */
                    String monthname1 = MONTHS[momth1];
                    String date1 = monthname1 + " " + day1 + "," + " " + year1;
                    String time2 = hour1 + " : " + minute1;
                    String datetime = time2 + "   " + date1;
                    calendar2 = Calendar.getInstance();

/** In this time set to alarm is compare to current time if t value is 1 it means the time set to alarm
 is after the current time and if t value is -1 it means alarm is set in past which is invalid selection
 and if t value is 0 it means both time are equal*/
                    t = calendar.compareTo(calendar2);


                    int cal = year1 + momth1 + day1 + hour1 + minute1;

                    if (t == 1) {

/** getalarmnumber() method returns the counting of alarms are set */
                        Cursor cursor2 = database1.getalarmnumber(sql);
                        if (cursor2.moveToFirst()) {
                            String notificationnumber = cursor2.getString(0);
                            x = Integer.parseInt(notificationnumber);
                            x++;
                            newnumber = Integer.toString(x);

/** setalarmnumber() method is used to set updated number of alarms where newnumber  is newcount and notification
 is past count into table 'tablename5' */
                            database1.setalarmnumber(sql, notificationnumber, newnumber);

                        }


/** setnotificationdata() is used to set alarm date into table 'tablename3' */
                        database1.setnotificationdata(id, sql, date, time, alarm_time, cal, datetime, newnumber, year2, momth2, day2, hour2, minute2);

                        int newnumber2 = Integer.parseInt(newnumber);

                        Context context = this.getApplicationContext();

                        datecounter = 0;
                        timecounter = 0;

                        if (alarm != null) {

/** set alarm by calling AlarmManagerBroadcast method setOnetimer() where alartime is  the time whose alarm is set
 and newnumber2 is the id of alarm*/
                            alarm.setOnetimeTimer(context, alarm_time, newnumber2);
                            Toast.makeText(getBaseContext(), "reminder  set", Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    } else {

                    }
                } else {
                    Toast.makeText(getBaseContext(), " choose  both  date & time", Toast.LENGTH_SHORT).show();
                    timecounter = 0;
                    datecounter = 0;


                }
            } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError oo)
        {

        }
    }



    /** This method is used to call the method oncreatedialog() to time picker dialog. */
    public void showtime(View view) {

        showDialog(dialog_id);


    }

    /** This method is used to call the method oncreatedialog() to date picker dialog. */
    public void showdate(View view) {

        showDialog(dialog_date);

    }






    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        //final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.notificationicon);
        //ab.setHomeAsUpIndicator(R.mipmap.delete);
        //ab.setDisplayHomeAsUpEnabled(true);
        //ab.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //       MenuInflater inflater = getMenuInflater();
        //     inflater.inflate(R.menu.menu_main,menu);
        getMenuInflater().inflate(R.menu.menu_showfetchvideo, menu);
        // Log.e(" onCreateO)"," onCreateOp)");
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(isTaskRoot());
        finish();

    }

    public void back(View view)
    {

        try {

            try {
                        moveTaskToBack(isTaskRoot());
                        finish();

            } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError oo)
        {

        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("request", "is" + requestCode);
        Log.e("request", "code" + resultCode);
         try {

         try {

        // When an Image is picked
        if (requestCode == 3) {
             videoUri = data.getData();
            intent = data.toUri(0);
            String path =  getRealPathFromURI(videoUri);
            Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            Log.e("bitmap", "is that" + bitmap + "path" + path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();
            editvideo();
            Toast.makeText(this, "Videeo successfully inserted", Toast.LENGTH_LONG).show();

        } else if (requestCode == 4) {
            Log.e("enter", "into choose video");
          videoUri = data.getData();
            intent = data.toUri(0);
            String path =  getRealPathFromURI(videoUri);
            Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            Log.e("bitmap", "is that" + bitmap + "path" + path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();
            editvideo();
            Toast.makeText(this, "Videeo successfully inserted", Toast.LENGTH_LONG).show();

        }

        else

        {
            Toast.makeText(this, "You haven't picked Video", Toast.LENGTH_LONG).show();
        }
            }
            catch (OutOfMemoryError i)
            {

            }
}


        catch (Exception e)
        {

        }

    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    /**  This method is used to make the changes in title and description of that note */
    public void menuedit(MenuItem item) throws OutOfMemoryError,Exception,IndexOutOfBoundsException {

        try {

            try {

                String title = et.getText().toString();
                String notetext = note.getText().toString();
                if (!intent.isEmpty()) {
                    String monthname = MONTHS[momth];
                    String time = monthname + " " + day + "," + " " + year;
                    Log.e("check","video"+intent+id+time+img+title+notetext);
                 //   int done = database1.upadatevideo(sql, intent, id,img,time,title,notetext);
                    Log.e("update","video text"+title+"note"+notetext);
                    Toast.makeText(getBaseContext(), "note  updated", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getBaseContext(), " No Video found ", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError oo)
        {

        }
    }


    /**  This method is used to make the changes in title and description of that note */
    public void editvideo() throws OutOfMemoryError,Exception,IndexOutOfBoundsException {

        try {

            try {
                String title = et.getText().toString();
                String notetext = note.getText().toString();
                if (!intent.isEmpty()) {
                    String monthname = MONTHS[momth];
                    String time = monthname + " " + day + "," + " " + year;
                  //  int done = database1.upadatevideo(sql, intent, id,img,time,title,notetext);
                    showfetchvideo();
                   // Toast.makeText(getBaseContext(), "note  updated", Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(getBaseContext(), " No Video found ", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError oo)
        {

        }
    }




    public void alert(MenuItem item) {

        Bundle args = new Bundle();
        args.putString("name", id);
        alertdialogdelete alertdialog = new alertdialogdelete();
        alertdialog.setArguments(args);
        alertdialog.show(getFragmentManager(), "my");

    }

    public void camera(MenuItem item) {

        try {
            final CharSequence[] items = { "make video","choose video","cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(showfetchvideo.this);
            builder.setTitle("Add Video!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("make video")) {
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }
                    else if (items[item].equals("choose video")) {
                        try {

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, RESULT_LOAD_VID);
                        }
                        catch (SecurityException l)
                        {

                        }
                    }
                    else if (items[item].equals("cancel")) {

                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
        catch (Exception r)
        {

        }

    }


    public void share(MenuItem item) {
        try {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, et.getText().toString());
                shareIntent.putExtra(Intent.EXTRA_TEXT, note.getText().toString());
                shareIntent.setType("video/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Log.e("location", "of file" + uri);
                startActivity(Intent.createChooser(shareIntent, "Share Video"));

            }



        catch (Exception e)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);;
            shareIntent.setType("text/");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "KeepNotes share"));
            Toast.makeText(getApplicationContext(), "Not able to share image", Toast.LENGTH_SHORT).show();

        }

        /*Bundle args = new Bundle();
        args.putString("name", intent);
       sharevideoalert sharevideoalert = new sharevideoalert();
        sharevideoalert.setArguments(args);
        sharevideoalert.show(getFragmentManager(), "my");*/

    }



}
