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
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
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

//import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.satay.dev.example.notes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.support.v7.widget.ShareActionProvider;

public class details extends AppCompatActivity {

/** declare  AlarmManagerBroadcast class object alarm; */
    private AlarmManagerBroadcast alarm;
    View v;
    String pass = null;
    File  newFile;
    static final int dialog_id = 0;
    private int REQUEST_VIDEO_CAPTURE = 3;
    private int RESULT_LOAD_VID = 4;
    static final int dialog_date = 1;
    int year, day, momth;
    int hour, minute,x;
    SQLiteDatabase sql;
    TextView tv;
    ImageView imageview,deleteimage,videopreview,play;
    FileProvider fileProvider = new FileProvider();
    Cursor cursor;
    EditText et, note;
    String name, pass1,check,newnumber;
    String table,dateshow;
    data data;
    private int RESULT_LOAD_IMG = 1,REQUEST_CAMERA = 2;
    String intent;
    ImageView imgView;
    int timecounter = 0,datecounter = 0,imagecounter = 0;
    String imgDecodableString,imagepath;
    int save = 0,video = 1;
    Uri uri,fileUri;
    Uri uri2;
    File image;
    File storageDir;
    FileOutputStream outputStream;
    Bitmap b1,resizedBitmap;
    String intentDescription, mCurrentPhotoPath;


    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String[] hour24 = {"", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private  byte[] img = null;
    private ShareActionProvider mShareActionProvider;
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (imageview==null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);
            imageview = (ImageView) findViewById(R.id.imgView);
            imageview.setVisibility(View.GONE);
            videopreview = (ImageView)findViewById(R.id.videopreview);
            videopreview.setVisibility(View.GONE);
            play = (ImageView)findViewById(R.id.play);
            play.setVisibility(View.GONE);
            deleteimage = (ImageView) findViewById(R.id.deleteimage);
            deleteimage.setVisibility(View.GONE);
            et = (EditText) findViewById(R.id.title);
            note = (EditText)findViewById(R.id.note);
/**  variable pass and check is used to store the variable values which is sent from mainactivity class
 and notification class respectively  wher pass store id and check used to determine that comes from mainac
 -tivity class and notification class */
            pass = getIntent().getStringExtra("id");
            check = getIntent().getStringExtra("id2");

Log.e("log","is"+pass);
/** data is the object of variable of dataclass. */
            data = new data(details.this);

/** same as in mainactivity class line 54. */
            sql = data.getWritableDatabase();

/** same as mainactivity line 125. */
            sql = data.getReadableDatabase();

/** alarm is initailised as object of AlarmanagerBroadcast class by calling its constructor. */
            alarm = new AlarmManagerBroadcast();

/** getTablename() method is used to get the name of table 'tablename'. */
            table = data.getTablename();


/** getdetaildata1(table,sql,pass) is called where table is name of table from whcich the data is fetch
 and pass is the id of the particular note whose data is fetch from  table 'tablename'      */

            if (cursor != null) {

            }
/** This is used to get the current year , day and month. */
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            momth = calendar.get(Calendar.MONTH);

/** This line is used to remove focus from edittext untill they are not click means keyboard is not
 show on screen*/
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

/** This method is called to get the data in detail about that note. */

            showindetail();

/** This method is used to set cuustom tolbar. */
            setupToolbar();


        }
        else{

        }
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



/** same as mainactivity line 70. */
   @Override
    public void onBackPressed() {

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



/** This is used to show custom toolbar. */
    private void setupToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show menu icon
         //final ActionBar ab = getSupportActionBar();
         // ab.setHomeAsUpIndicator(R.drawable.notificationicon);
        //ab.setHomeAsUpIndicator(R.mipmap.delete);
         //ab.setDisplayHomeAsUpEnabled(true);
        //ab.setDisplayHomeAsUpEnabled(true);
    }


/**same as mainactivity class .*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //       MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_details, menu);

        //     inflater.inflate(R.menu.menu_main,menu);
       MenuItem item = menu.findItem(R.id.share);

        mShareActionProvider = (ShareActionProvider)  MenuItemCompat.getActionProvider(item);


        return true;

    }







/** This mehod show data in detail. */
public  void showindetail() {
   // try {

//try {

    /*createImageFile1();
    // if (storageDir.exists())
    {
        storageDir.delete();
        Log.e("delested","completely"+storageDir);
    }*/
Log.e("check", "is " + check);
  //  videopreview.setVisibility(View.VISIBLE);
  //  play.setVisibility(View.VISIBLE);
    int videocheck = Integer.parseInt(check);
    if (videocheck==1) {
        Cursor cursor = data.fetchvideo(sql, pass);
        Log.e("show", "videoy o89o9 9" + cursor.getCount());
        if ((cursor.getCount()) > 0) {
            if (cursor.moveToFirst()) {
                video = 1;
                String vid = cursor.getString(1);
                intent = cursor.getString(0);
                String title = cursor.getString(2);
                String notetext = cursor.getString(3);
                img = cursor.getBlob(cursor.getColumnIndex("preview"));
                if (title != null) {
                    et.setText(title, TextView.BufferType.EDITABLE);
                    note.setText(notetext, TextView.BufferType.EDITABLE);
                    note.setFocusable(true);
                    note.setMovementMethod(LinkMovementMethod.getInstance());
                }
                Bitmap videopreviewbitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                try {

                    Log.e("show", "video2" + intent + "hjdhjewhd" + vid);
                    Intent intent1 = Intent.parseUri(intent, 0);
                    uri = intent1.getData();
                    Log.e("show", "video34" + imageview + "hjdhjewhd" + img);
                    videopreview.setVisibility(View.VISIBLE);
                    videopreview.setImageBitmap(videopreviewbitmap);
                    play.setVisibility(View.VISIBLE);
                    deleteimage.setVisibility(View.VISIBLE);
                    Log.e("show", "video56" + videopreviewbitmap);
                } catch (Exception h) {
                    Log.e("show fetch", "video error" + h);
                }

            }
        }
    }
    else {
        Log.e("show fetch", "video error"  );
        cursor = data.getdetaildata1(table, sql, pass);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                img = null;
/** variable name and pass1 store title and message respectively. */
                name = cursor.getString(0);
                pass1 = cursor.getString(1);
Log.e("title","& note"+name+"plus"+pass1);
                img = cursor.getBlob(cursor.getColumnIndex("image"));
                imagepath = cursor.getString(6);
                intentDescription = cursor.getString(7);

              //  String time1 = cursor.getString(3);
                String cancel = cursor.getString(4);
                //Log.e("time1","is eqaul "+time1);
                //int x1 = Integer.parseInt(time1);

                if ((img != null)) {
                    b1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                    imageview.setVisibility(View.VISIBLE);
                    imageview.setImageBitmap(b1);
                    imagecounter = 1;
            /*final File    imagePath1 = new File(details.this.getFilesDir(), "storeimages");
                Log.e("directory","path"+imagePath1);
                if( !imagePath1.exists() ) {
                    imagePath1.mkdirs();
                    Log.e("not", "exist" );

                }
                else if(!imagePath1.isDirectory() && imagePath1.canWrite() ){
                    imagePath1.delete();
                    imagePath1.mkdirs();
                    Log.e("badly", "happen" );
                }
                else{
                    //you can't access there with write permission.
                    //Try other way.
                }
            newFile = new File(imagePath1, pass);

                if( newFile.exists() ) {

                }
                else if( newFile.isDirectory() && newFile.canWrite() ){

                }
                else{
                    //you can't access there with write permission.
                    //Try other way.
                }

               try {
    //outputStream = new FileOutputStream(newFile);
                }
                catch (Exception r)
                {
Log.e("errors","in the case"+r);
                }
Boolean s = newFile.exists();
              Boolean n =   newFile.isFile();
String path = newFile.getPath();
                Log.e("path","in "+s+n+path);
             //  b1.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

               //Uri contentUri = getUriForFile(details.this, "com.KeepNotestesting", newFile);
                //uri = ;
                //Log.e("content","uri"+uri);*/
                    readBitmapInfo(b1);

                }

           //     if (x1 == 1) {
             //       data.deletenotificationentry(sql, cancel);
                    /** variable  time1 is used  to indicate that notification  is check by  the user . */
                    String time2 = "0";

/** upadatenotificationumberdelete(sql,id2,time1); is used to  update table 'tablename2 with the variable time1'    */
               //     data.upadatenotificationumberdelete(sql, pass, time2);

                //}

/**   These two lines is used to show data using edittext and also write data with these edittext*/
                et.setText(name, TextView.BufferType.EDITABLE);
                note.setText(pass1, TextView.BufferType.EDITABLE);
                note.setFocusable(true);
                note.setMovementMethod(LinkMovementMethod.getInstance());

            }
        }
    }
/*}
catch (OutOfMemoryError o)
{

}
    }
    catch (Exception e)
    {

    }*/

}


    public void playvideo(View view) {

        Intent intent1 = new Intent(details.this,playvideo.class);
        intent1.putExtra("videouri", pass);
        intent1.putExtra("activity","showvideo");
        startActivity(intent1);

    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** Handle action bar item clicks here. The action bar will
            automatically handle clicks on the Home/Up button, so long
            as you specify a parent activity in AndroidManifest.xml. */
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static float megabytesFree() {
        final Runtime rt = Runtime.getRuntime();
        final float bytesUsed = rt.totalMemory();
        final float mbUsed = bytesUsed/BYTES_IN_MB;
        final float mbFree = megabytesAvailable() - mbUsed;
        return mbFree;
    }

    public static float megabytesAvailable() {
        final Runtime rt = Runtime.getRuntime();
        final float bytesAvailable = rt.maxMemory();
        return bytesAvailable/BYTES_IN_MB;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


/** This method is used to set repeating alarm after certain interval. */
    public void startRepeatingTimer(View view) {

        Context context = this.getApplicationContext();

        if(alarm != null){
            alarm.SetAlarm(context);
        }
        else{
          //Toast.makeText(getBaseContext(), "alarm start", Toast.LENGTH_SHORT).show();
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






/** This method is used to call the method oncreatedialog() to time picker dialog. */
    public void showtime(View view) {

        showDialog(dialog_id);


}

    /** This method is used to call the method oncreatedialog() to date picker dialog. */
    public void showdate(View view) {

        showDialog(dialog_date);

    }


    public void videoedit()
    {
        try {
            try {

                String title1 = et.getText().toString();
                String note1 = note.getText().toString();

                String monthname = MONTHS[momth];
                String time = monthname + " " + day + "," + " " + year;
              String videosign = String.valueOf(video);
                    int done = data.upadatevideo(sql, intent, pass,img,time,title1,note1,videosign);
showindetail();


            }
            catch (OutOfMemoryError o)
            {

            }
        }
        catch (Exception e)
        {

        }
    }

/**  This method is used to make the changes in title and description of that note */
    public void menuedit(MenuItem item) throws OutOfMemoryError,Exception,IndexOutOfBoundsException {

        try {

            try {
                String title1 = et.getText().toString();
                String note1 = note.getText().toString();

                String monthname = MONTHS[momth];
                String time = monthname + " " + day + "," + " " + year;
                if (video==1)
                {
                    String videosign = String.valueOf(video);
                    int done = data.upadatevideo(sql, intent, pass,img,time,title1,note1,videosign);
                }

                else {
                    if ((!title1.isEmpty()) && (!note1.isEmpty())) {

                        sql = data.getWritableDatabase();
                        Log.e("img", "path" + img + imgDecodableString);
                        int done = data.upadate(title1, note1, name, sql, time, img, imgDecodableString, pass, intent);

                        Toast.makeText(getBaseContext(), "note  updated", Toast.LENGTH_SHORT).show();
                        save = 1;
                    } else {
                        Toast.makeText(getBaseContext(), " Enter  title & note ", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError oo)
        {

        }
    }


/** This method is called to move mainactivity class. */
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


    public void alert(MenuItem item) {

        Bundle args = new Bundle();
        args.putString("name", pass);
        alertdialogdelete alertdialog = new alertdialogdelete();
        alertdialog.setArguments(args);
        alertdialog.show(getFragmentManager(), "my");

    }


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
                        Cursor cursor2 = data.getalarmnumber(sql);
                        if (cursor2.moveToFirst()) {
                            String notificationnumber = cursor2.getString(0);
                            x = Integer.parseInt(notificationnumber);
                            x++;
                            newnumber = Integer.toString(x);

/** setalarmnumber() method is used to set updated number of alarms where newnumber  is newcount and notification
 is past count into table 'tablename5' */
                            data.setalarmnumber(sql, notificationnumber, newnumber);

                        }


/** setnotificationdata() is used to set alarm date into table 'tablename3' */
                        data.setnotificationdata(pass, sql, date, time, alarm_time, cal, datetime, newnumber, year2, momth2, day2, hour2, minute2);

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

public void imageedit()
{

    try {

        try {


            String title1 = et.getText().toString();
            String note1 = note.getText().toString();

            if ((!title1.isEmpty()) && (!note1.isEmpty())) {

                String monthname = MONTHS[momth];
                String time = monthname + " " + day + "," + " " + year;
                sql = data.getWritableDatabase();
                Log.e("img", "path" + img + imgDecodableString);
                int done = data.upadate(title1, note1, name, sql, time, img, imgDecodableString, pass,intent);
showindetail();

                save = 1;
            } else {

            }
        } catch (Exception e) {

        }
    }
    catch (OutOfMemoryError oo)
    {

    }

}


    public Bitmap resizeimage(float totalmemory,Bitmap picture,float imageamonut)
    {

        Bitmap b = picture;
        float mbTotal = totalmemory;
        float imagetype = imageamonut;
        if ((mbTotal >= 96.0) && (mbTotal <= 120.0)) {

            if ((imagetype >= 9.0) && (imagetype < 10.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.3), (int) (b.getHeight() * 0.3), false);
                Log.e("bw", "9.0 & 10.0");
            } else if ((imagetype >= 8.0) && (imagetype < 8.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.4), (int) (b.getHeight() * 0.4), false);
                Log.e("bw ", "8.0 & 8.5");
            } else if ((imagetype >= 8.5) && (imagetype < 9.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.35), (int) (b.getHeight() * 0.35), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if (imagetype >= 10.0) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.2), (int) (b.getHeight() * 0.2), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 7.0) && (imagetype < 7.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.45), (int) (b.getHeight() * 0.45), false);
                Log.e("bw", "7.0 & 7.5");
            } else if ((imagetype >= 6.5) && (imagetype < 7.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("bw ", "6.5 & 7.0");
            } else if ((imagetype >= 7.5) && (imagetype < 8.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.55), (int) (b.getHeight() * 0.55), false);
                Log.e("bw ", "7.5 & 8.5");
            } else if ((imagetype >= 6.0) && (imagetype < 6.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.6), (int) (b.getHeight() * 0.6), false);
                Log.e("bw", "6.0 & 7.0");
            } else if ((imagetype >= 5.0) && (imagetype < 6.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.65), (int) (b.getHeight() * 0.65), false);
                Log.e("bw", "5.0 & 6.0");
            } else if ((imagetype >= 4.0) && (imagetype < 5.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw", "bw 4.0 & 5.0");
            } else if ((imagetype >= 3.0) && (imagetype < 4.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.75), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "3.0 & 4.0");
            } else if ((imagetype >= 2.0) && (imagetype < 3.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.8), (int) (b.getHeight() * 0.8), false);
                Log.e("bw", "2.0 & 3.0");
            } else if ((imagetype >= 1.0) && (imagetype < 2.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.85), (int) (b.getHeight() * 0.85), false);
                Log.e("bw 96 and", "1.0 & 2.0");
            } else if ((imagetype >= 0.5) && (imagetype < 1.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.9), (int) (b.getHeight() * 0.9), false);
                Log.e("bw", "0.5 & 1.0");
            } else if ((imagetype >= 0.1) && (imagetype < 0.5)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw", "less than 0.5");
            } else {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.4), (int) (b.getHeight() * 0.4), false);
                Log.e("bw", "elseeeeeee");
            }

        } else if ((mbTotal > 120.0) && (mbTotal <= 144.0)) {
            Log.e("enter into less ", "than 144");
            if ((imagetype >= 9.0) && (imagetype < 9.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.45), (int) (b.getHeight() * 0.45), false);
                Log.e("bw", "9.0 & 9.5");
            }

            if ((imagetype >= 9.5) && (imagetype < 10.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.4), (int) (b.getHeight() * 0.4), false);
                Log.e("bw", "9.0 & 10.0");
            } else if ((imagetype >= 8.0) && (imagetype < 8.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.55), (int) (b.getHeight() * 0.55), false);
                Log.e("bw ", "8.0 & 8.5");
            } else if ((imagetype >= 8.5) && (imagetype < 9.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if ((imagetype >= 11) && (imagetype < 12)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.45), (int) (b.getHeight() * 0.45), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 12) && (imagetype < 13)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.4), (int) (b.getHeight() * 0.4), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 13) && (imagetype < 14)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.35), (int) (b.getHeight() * 0.35), false);
                Log.e("greater ", "than 10.0");
            } else if (imagetype >= 14) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.25), (int) (b.getHeight() * 0.25), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 10) && (imagetype < 11)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 7.0) && (imagetype < 7.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.65), (int) (b.getHeight() * 0.65), false);
                Log.e("bw", "7.0 & 7.5");
            } else if ((imagetype >= 6.5) && (imagetype < 7.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw ", "6.5 & 7.0");
            } else if ((imagetype >= 7.5) && (imagetype < 8.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.6), (int) (b.getHeight() * 0.6), false);
                Log.e("bw ", "7.5 & 8.5");
            } else if ((imagetype >= 6.0) && (imagetype < 6.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.75), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "6.0 & 7.0");
            } else if ((imagetype >= 5.0) && (imagetype < 6.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.8), (int) (b.getHeight() * 0.8), false);
                Log.e("bw", "5.0 & 6.0");
            } else if ((imagetype >= 4.0) && (imagetype < 5.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.85), (int) (b.getHeight() * 0.85), false);
                Log.e("bw", "bw 4.0 & 5.0");
            } else if ((imagetype >= 3.0) && (imagetype < 4.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.9), (int) (b.getHeight() * 0.9), false);
                Log.e("bw", "3.0 & 4.0");
            } else if ((imagetype >= 2.0) && (imagetype < 3.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.95), (int) (b.getHeight() * 0.95), false);
                Log.e("bw", "2.0 & 3.0");
            } else if ((imagetype >= 1.0) && (imagetype < 2.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.85), (int) (b.getHeight() * 0.85), false);
                Log.e("bw", "1.0 & 2.0");
            } else if ((imagetype >= 0.5) && (imagetype < 1.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.9), (int) (b.getHeight() * 0.9), false);
                Log.e("bw exact got it", "0.5 & 1.0");
            } else if ((imagetype >= 0.1) && (imagetype < 0.5)) {
                resizedBitmap = b;// b.createScaledBitmap(b, (int) (b.getWidth() * 0.95), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "less than 0.5");
            } else {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.45), (int) (b.getHeight() * 0.45), false);
                Log.e("bw", "elseeeeeee");
            }

        } else if ((mbTotal > 144.0) && (mbTotal <= 168.0)) {
            Log.e("enter into less ", "than 168");
            if ((imagetype >= 9.0) && (imagetype < 9.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.55), (int) (b.getHeight() * 0.55), false);
                Log.e("bw", "9.0 & 9.5");
            }

            if ((imagetype >= 9.5) && (imagetype < 10.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("bw", "9.0 & 10.0");
            } else if ((imagetype >= 8.0) && (imagetype < 8.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.65), (int) (b.getHeight() * 0.65), false);
                Log.e("bw ", "8.0 & 8.5");
            } else if ((imagetype >= 8.5) && (imagetype < 9.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.6), (int) (b.getHeight() * 0.6), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if ((imagetype >= 10.0) && (imagetype < 11.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.45), (int) (b.getHeight() * 0.45), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if ((imagetype >= 11.0) && (imagetype < 12.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.4), (int) (b.getHeight() * 0.4), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if (imagetype >= 12.0) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.3), (int) (b.getHeight() * 0.3), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 7.0) && (imagetype < 7.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.75), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "7.0 & 7.5");
            } else if ((imagetype >= 6.5) && (imagetype < 7.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.8), (int) (b.getHeight() * 0.8), false);
                Log.e("bw ", "6.5 & 7.0");
            } else if ((imagetype >= 7.5) && (imagetype < 8.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw ", "7.5 & 8.5");
            } else if ((imagetype >= 6.0) && (imagetype < 6.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.8), (int) (b.getHeight() * 0.8), false);
                Log.e("bw", "6.0 & 7.0");
            } else if ((imagetype >= 5.0) && (imagetype < 6.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.85), (int) (b.getHeight() * 0.85), false);
                Log.e("bw", "5.0 & 6.0");
            } else if ((imagetype >= 4.0) && (imagetype < 5.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.9), (int) (b.getHeight() * 0.9), false);
                Log.e("bw", "bw 4.0 & 5.0");
            } else if ((imagetype >= 3.0) && (imagetype < 4.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.95), (int) (b.getHeight() * 0.95), false);
                Log.e("bw", "3.0 & 4.0");
            } else if ((imagetype >= 2.0) && (imagetype < 3.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.39), (int) (b.getHeight() * 0.52), false);
                Log.e("bw", "2.0 & 3.0");
            } else if ((imagetype >= 1.0) && (imagetype < 2.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.58), (int) (b.getHeight() * 0.58), false);
                Log.e("bw", "1.0 & 2.0");
            } else if ((imagetype >= 0.5) && (imagetype < 1.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.68), (int) (b.getHeight() * 0.68), false);
                Log.e("bw case of 144", "0.5 & 1.0");
            } else if ((imagetype >= 0.1) && (imagetype < 0.5)) {
                resizedBitmap = b;// b.createScaledBitmap(b, (int) (b.getWidth() * 0.78), (int) (b.getHeight() * 0.78), false);
                Log.e("bw", "less than 0.5");
            } else {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.6), (int) (b.getHeight() * 0.6), false);
                Log.e("bw", "elseeeeeee");
            }

        } else if ((mbTotal > 168.0) && (mbTotal <= 192.0)) {
            Log.e("enter into less ", "than 192");
            if ((imagetype >= 9.0) && (imagetype < 9.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.55), (int) (b.getHeight() * 0.55), false);
                Log.e("bw", "9.0 & 9.5");
            }

            if ((imagetype >= 9.5) && (imagetype < 10.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("bw", "9.0 & 10.0");
            } else if ((imagetype >= 8.0) && (imagetype < 8.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.65), (int) (b.getHeight() * 0.65), false);
                Log.e("bw ", "8.0 & 8.5");
            } else if ((imagetype >= 8.5) && (imagetype < 9.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.6), (int) (b.getHeight() * 0.6), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if ((imagetype >= 10.0) && (imagetype < 11.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if ((imagetype >= 11.0) && (imagetype < 12.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.45), (int) (b.getHeight() * 0.45), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if (imagetype >= 12.0) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.35), (int) (b.getHeight() * 0.35), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 7.0) && (imagetype < 7.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.75), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "7.0 & 7.5");
            } else if ((imagetype >= 6.5) && (imagetype < 7.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.8), (int) (b.getHeight() * 0.8), false);
                Log.e("bw ", "6.5 & 7.0");
            } else if ((imagetype >= 7.5) && (imagetype < 8.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw ", "7.5 & 8.5");
            } else if ((imagetype >= 6.0) && (imagetype < 6.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.85), (int) (b.getHeight() * 0.85), false);
                Log.e("bw", "6.0 & 7.0");
            } else if ((imagetype >= 5.0) && (imagetype < 6.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.9), (int) (b.getHeight() * 0.9), false);
                Log.e("bw", "5.0 & 6.0");
            } else if ((imagetype >= 4.0) && (imagetype < 5.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.95), (int) (b.getHeight() * 0.95), false);
                Log.e("bw", "bw 4.0 & 5.0");
            } else if ((imagetype >= 3.0) && (imagetype < 4.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.39), (int) (b.getHeight() * 0.49), false);
                Log.e("bw", "3.0 & 4.0");
            } else if ((imagetype >= 2.0) && (imagetype < 3.0)) {
                resizedBitmap = b;// b.createScaledBitmap(b, (int) (b.getWidth() * 0.41), (int) (b.getHeight() * 0.54), false);
                Log.e("bw", "2.0 & 3.0");
            } else if ((imagetype >= 1.0) && (imagetype < 2.0)) {
                resizedBitmap = b;// b.createScaledBitmap(b, (int) (b.getWidth() * 0.60), (int) (b.getHeight() * 0.60), false);
                Log.e("bw case of 1968", "1.0 & 2.0");
            } else if ((imagetype >= 0.5) && (imagetype < 1.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.72), (int) (b.getHeight() * 0.72), false);
                Log.e("bw", "0.5 & 1.0");
            } else if ((imagetype >= 0.1) && (imagetype < 0.5)) {
                resizedBitmap = b;
                b.createScaledBitmap(b, (int) (b.getWidth() * 0.82), (int) (b.getHeight() * 0.82), false);
                Log.e("bw", "less than 0.5");
            } else {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw", "elseeeeeee");
            }

        }

        if ((mbTotal > 192.0)) {
            Log.e("enter into greater ", "than 192");
            if ((imagetype >= 9.0) && (imagetype < 9.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.6), (int) (b.getHeight() * 0.6), false);
                Log.e("bw", "9.0 & 9.5");
            }

            if ((imagetype >= 9.5) && (imagetype < 10.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.55), (int) (b.getHeight() * 0.55), false);
                Log.e("bw", "9.0 & 10.0");
            } else if ((imagetype >= 8.0) && (imagetype < 8.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.7), (int) (b.getHeight() * 0.7), false);
                Log.e("bw ", "8.0 & 8.5");
            } else if ((imagetype >= 8.5) && (imagetype < 9.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.65), (int) (b.getHeight() * 0.65), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if (imagetype >= 10.0) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.5), (int) (b.getHeight() * 0.5), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 7.0) && (imagetype < 7.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.75), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "7.0 & 7.5");
            } else if ((imagetype >= 6.5) && (imagetype < 7.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.8), (int) (b.getHeight() * 0.8), false);
                Log.e("bw ", "6.5 & 7.0");
            } else if ((imagetype >= 7.5) && (imagetype < 8.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.85), (int) (b.getHeight() * 0.85), false);
                Log.e("bw ", "7.5 & 8.5");
            } else if ((imagetype >= 6.0) && (imagetype < 6.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.9), (int) (b.getHeight() * 0.9), false);
                Log.e("bw", "6.0 & 7.0");
            } else if ((imagetype >= 5.0) && (imagetype < 6.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.95), (int) (b.getHeight() * 0.95), false);
                Log.e("bw", "5.0 & 6.0");
            } else if ((imagetype >= 4.0) && (imagetype < 5.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.37), (int) (b.getHeight() * 0.41), false);
                Log.e("bw", "bw 4.0 & 5.0");
            } else if ((imagetype >= 3.0) && (imagetype < 4.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.41), (int) (b.getHeight() * 0.51), false);
                Log.e("bw", "3.0 & 4.0");
            } else if ((imagetype >= 2.0) && (imagetype < 3.0)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.43), (int) (b.getHeight() * 0.56), false);
                Log.e("bw", "2.0 & 3.0");
            } else if ((imagetype >= 1.0) && (imagetype < 2.0)) {
                resizedBitmap = b;// b.createScaledBitmap(b, (int) (b.getWidth() * 0.62), (int) (b.getHeight() * 0.62), false);
                Log.e("bw", "1.0 & 2.0");
            } else if ((imagetype >= 0.5) && (imagetype < 1.0)) {
                resizedBitmap = b;// b.createScaledBitmap(b, (int) (b.getWidth() * 0.74), (int) (b.getHeight() * 0.74), false);
                Log.e("bw case of 192", "0.5 & 1.0");
            } else if ((imagetype >= 0.1) && (imagetype < 0.5)) {
                resizedBitmap = b;//b.createScaledBitmap(b, (int) (b.getWidth() * 0.84), (int) (b.getHeight() * 0.84), false);
                Log.e("bw", "less than 0.5");
            } else {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.75), (int) (b.getHeight() * 0.75), false);
                Log.e("bw", "elseeeeeee");
            }

        } else if (mbTotal < 96.0) {
            Log.e("enter into less ", "than 96");
            if ((imagetype >= 9.0) && (imagetype < 9.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.15), (int) (b.getHeight() * 0.15), false);
                Log.e("bw", "9.0 & 9.5");
            }

            if ((imagetype >= 9.5) && (imagetype < 10.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.13), (int) (b.getHeight() * 0.13), false);
                Log.e("bw", "9.0 & 10.0");
            } else if ((imagetype >= 8.0) && (imagetype < 8.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.19), (int) (b.getHeight() * 0.19), false);
                Log.e("bw ", "8.0 & 8.5");
            } else if ((imagetype >= 8.5) && (imagetype < 9.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.17), (int) (b.getHeight() * 0.17), false);
                Log.e("bw ", "8.5 & 9.0");
            } else if (imagetype >= 10.0) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.11), (int) (b.getHeight() * 0.11), false);
                Log.e("greater ", "than 10.0");
            } else if ((imagetype >= 7.0) && (imagetype < 7.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.23), (int) (b.getHeight() * 0.23), false);
                Log.e("bw", "7.0 & 7.5");
            } else if ((imagetype >= 6.5) && (imagetype < 7.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.25), (int) (b.getHeight() * 0.25), false);
                Log.e("bw ", "6.5 & 7.0");
            } else if ((imagetype >= 7.5) && (imagetype < 8.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.20), (int) (b.getHeight() * 0.20), false);
                Log.e("bw ", "7.5 & 8.5");
            } else if ((imagetype >= 6.0) && (imagetype < 6.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.25), (int) (b.getHeight() * 0.25), false);
                Log.e("bw", "6.0 & 7.0");
            } else if ((imagetype >= 5.0) && (imagetype < 6.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.27), (int) (b.getHeight() * 0.29), false);
                Log.e("bw", "5.0 & 6.0");
            } else if ((imagetype >= 4.0) && (imagetype < 5.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.29), (int) (b.getHeight() * 0.35), false);
                Log.e("bw", "bw 4.0 & 5.0");
            } else if ((imagetype >= 3.0) && (imagetype < 4.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.31), (int) (b.getHeight() * 0.43), false);
                Log.e("bw", "3.0 & 4.0");
            } else if ((imagetype >= 2.0) && (imagetype < 3.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.33), (int) (b.getHeight() * 0.48), false);
                Log.e("bw", "2.0 & 3.0");
            } else if ((imagetype >= 1.0) && (imagetype < 2.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.48), (int) (b.getHeight() * 0.46), false);
                Log.e("bw less than 96", "1.0 & 2.0");
            } else if ((imagetype >= 0.5) && (imagetype < 1.0)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.58), (int) (b.getHeight() * 0.58), false);
                Log.e("bw", "0.5 & 1.0");
            } else if ((imagetype >= 0.1) && (imagetype < 0.5)) {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.58), (int) (b.getHeight() * 0.58), false);
                Log.e("bw", "less than 0.5");
            } else {
                resizedBitmap = b.createScaledBitmap(b, (int) (b.getWidth() * 0.38), (int) (b.getHeight() * 0.38), false);
                Log.e("bw", "elseeeeeee");
            }

        }
        return resizedBitmap;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
try {


    try {


        // When an Image is picked
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data) {
            videopreview.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            deleteimage.setVisibility(View.GONE);
            video = 0;
            img = null;
            intent = null;
            check = "2";
            videoedit();
            // Get the Image from data
            intent = data.toUri(0);
             uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();

            // Set the Image in ImageView after decoding the String

            Bitmap b = BitmapFactory.decodeFile(imgDecodableString);

            float imagetype = readBitmapInfo(b);

            final float mbTotal = megabytesAvailable();
            final String totAvailable = "total MB available: " + mbTotal;
            Log.e("main", "ory total" + totAvailable);
            final float mbFree = megabytesFree();
            final String totFree = "free MB available: " + mbFree;
            Log.e("main", "in mb" + totFree);

            Log.e("beefor", "resized detail");
            readBitmapInfo(b);


            readBitmapInfo(resizedBitmap);
            resizedBitmap = resizeimage(mbTotal,b,imagetype);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();
            imageedit();
        }
        else  if (requestCode == 2)
        {
            videopreview.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            deleteimage.setVisibility(View.GONE);
            video = 0;
            img = null;
            intent = null;
            check = "2";
            videoedit();
            Log.e("enter", "into camera");
            Log.e("camera","picture"+fileUri);

            Bitmap yourSelectedImage = BitmapFactory.decodeFile(fileUri.getPath());
            if (yourSelectedImage!=null) {
                float imagetype = readBitmapInfo(yourSelectedImage);
                final float mbTotal = megabytesAvailable();
                final String totAvailable = "total MB available: " + mbTotal;
                Log.e("main", "ory total" + totAvailable);
                final float mbFree = megabytesFree();
                final String totFree = "free MB available: " + mbFree;
                Log.e("main", "in mb" + yourSelectedImage);

                resizedBitmap = resizeimage(mbTotal, yourSelectedImage, imagetype);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                readBitmapInfo(resizedBitmap);
                // imgView.setVisibility(View.VISIBLE);

                //imgView.setImageBitmap(resizedBitmap);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                img = bos.toByteArray();
                Toast.makeText(this, "camera take image ",
                        Toast.LENGTH_LONG).show();
                if (resizedBitmap != null) {
                    imageedit();
                }

                if (image.exists())
                {
                    image.delete();
                   // Toast.makeText(this, "file completely deleted",
                        //    Toast.LENGTH_LONG).show();
                }
            }
            if (image.exists())
            {
                image.delete();
                Toast.makeText(this, "file completely deleted",
                        Toast.LENGTH_LONG).show();
            }
        }

       else if (requestCode == 3) {
            video = 1;
            imageview.setVisibility(View.GONE);
            Uri videoUri = data.getData();
            uri = videoUri;
            intent = data.toUri(0);
            String path =  getRealPathFromURI(videoUri);
            Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            Log.e("bitmap", "is that" + bitmap + "path" + path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            videopreview.setVisibility(View.VISIBLE);
            videopreview.setImageBitmap(bitmap);
            play.setVisibility(View.VISIBLE);
            deleteimage.setVisibility(View.VISIBLE);
            img = bos.toByteArray();
            videoedit();
            //  playvideo(videoUri);
        }
        else if (requestCode == 4) {

            video = 1 ;
            Uri videoUri = data.getData();
            imageview.setVisibility(View.GONE);
            uri = videoUri;
            intent = data.toUri(0);
            String path =  getRealPathFromURI(videoUri);
            Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            Log.e("bitmap", "is that" + bitmap + "path" + path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            videopreview.setVisibility(View.VISIBLE);
            videopreview.setImageBitmap(bitmap);
            play.setVisibility(View.VISIBLE);
            deleteimage.setVisibility(View.VISIBLE);
            img = bos.toByteArray();
            //  playvideo(videoUri);
            videoedit();
            Toast.makeText(this, "Videeo successfully inserted", Toast.LENGTH_LONG).show();

        }

        else

        {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }

    } catch (Exception ee) {

    }
}
catch (OutOfMemoryError o)
{

}
    }



    public void deletevideo(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(details.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this video ?");

        // Setting Icon to Dialog


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "video deleted", Toast.LENGTH_SHORT).show();
                videopreview.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                deleteimage.setVisibility(View.GONE);
                video = 0;
                img = null;
                intent = null;
                videoedit();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    private float readBitmapInfo(Bitmap bm) {
           float estimatedimagesize = 0;

try {

    try {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        float BYTES_PER_PX = 4.0f; //32 bit

        float imageHeight = bm.getHeight();
        float imageWidth = bm.getWidth();
        String imageMimeType = options.outMimeType;
        estimatedimagesize = imageWidth * imageHeight * BYTES_PER_PX / BYTES_IN_MB;
        Log.e("dlfkf", "w,h, type:" + imageWidth + ", " + imageHeight + ", " + imageMimeType);
        Log.e("dkslkk", "estimated memory required in MB: " + imageWidth * imageHeight * BYTES_PER_PX / BYTES_IN_MB);
        return estimatedimagesize;
    } catch (Exception e) {

    }
}
catch (OutOfMemoryError oo)
{

}
           return estimatedimagesize;

    }

    private File createImageFile1() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "hello how are you" + timeStamp + "_";
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "hello how are you" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
    image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }



    public void takepicturefromgallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Start the Intent

        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    public void camera(MenuItem item) {

        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library","make video","Choose video", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(details.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {

                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Ensure that there's a camera activity to handle the intent
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File

                            }
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(photoFile));
                                fileUri = Uri.fromFile(photoFile);
                                Log.e("now chck this","cool"+fileUri);
                                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                            }
                        }

                    } else if (items[item].equals("Choose from Library")) {
                        takepicturefromgallery();
                    }

                    else if (items[item].equals("make video")) {
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }

                    else if (items[item].equals("Choose video")) {
                        try {

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, RESULT_LOAD_VID);
                        }
                        catch (SecurityException l)
                        {

                        }
                    }

                    else if (items[item].equals("Cancel")) {
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
//try {
    Intent shareIntent = new Intent();

    shareIntent.setAction(Intent.ACTION_SEND);
File storeimage = null;
    Log.e("imagecounter","value"+imagecounter);

    if (video==1)
    {
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, et.getText().toString());
        shareIntent.putExtra(Intent.EXTRA_TEXT, note.getText().toString());
        shareIntent.setType("video/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Log.e("location", "of file" + uri);
        startActivity(Intent.createChooser(shareIntent, "Share Video"));

    }
    if ((imagecounter==1) && (pass1 != null)) {
        Uri imageuri = null;
        shareIntent.putExtra(Intent.EXTRA_TEXT, pass1);
        try {
            storeimage = createImageFile();
            FileOutputStream outputStream2 = new FileOutputStream(storeimage);
            b1.compress(Bitmap.CompressFormat.JPEG, 100, outputStream2);
      imageuri = Uri.fromFile(storeimage);
        }
        catch (Exception y)
        {

        }
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageuri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, et.getText().toString());
        shareIntent.setType("image/*");
       // getBaseContext().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Log.e("location", "of file" + imageuri);
        Log.e("path", "of file" + storeimage.getPath());
        startActivity(Intent.createChooser(shareIntent, "Share image & text"));

    }

    else if (pass1 != null) {
        shareIntent.putExtra(Intent.EXTRA_TEXT, pass1);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, et.getText().toString());
        shareIntent.setType("text/");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share text"));

    } else if (imagecounter==1) {
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, et.getText().toString());
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

    }

 //   Toast.makeText(getApplicationContext(), "image file created successfull", Toast.LENGTH_SHORT).show();

/*}
catch (Exception e)
{
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, name);
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, et.getText().toString());
    shareIntent.setType("text/");
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    startActivity(Intent.createChooser(shareIntent, "KeepNotes share"));
    Toast.makeText(getApplicationContext(), "Not able to share image", Toast.LENGTH_SHORT).show();

}*/

    }


    public void deleteimage(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(details.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this image ?");

        // Setting Icon to Dialog


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "image deleted", Toast.LENGTH_SHORT).show();
                deleteimage.setVisibility(View.GONE);
                imageview.setVisibility(View.GONE);
                img = null;
                uri = null;
                imgDecodableString = null;
                imageedit();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


}
