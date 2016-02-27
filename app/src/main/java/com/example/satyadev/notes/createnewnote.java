package com.example.satyadev.notes;

import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import com.satay.dev.example.notes.R;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.graphics.Bitmap;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class createnewnote extends AppCompatActivity {
    EditText edittitle, editnote;
    int year, day, momth,video=0;
    data database1;
    ImageView imgView,deleteimage,videopreview,play;
    SQLiteDatabase sql;
    String title = null, note = null;
    private int RESULT_LOAD_IMG = 1,save = 0, RESULT_LOAD_VID = 4;
    private int REQUEST_CAMERA = 2;
    private int REQUEST_VIDEO_CAPTURE = 3;
    Bitmap resizedBitmap;
    ProgressDialog pDialog;
    String imgDecodableString;
    private byte[] img = null;
    private byte[] imageInByte = null;
    String intent,mCurrentPhotoPath;
    Uri selectedImage,fileUri;
    ContentValues   values;
    File photoFile = null;
    VideoView videoView;
    String VideoURL = "https://www.youtube.com/watch?v=ZVvmLakUtXE";
    File imagefile,image;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewnote);
        database1 = new data(createnewnote.this);
        sql = database1.getWritableDatabase();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edittitle = (EditText) findViewById(R.id.title);
        editnote = (EditText) findViewById(R.id.note);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        momth = calendar.get(Calendar.MONTH);
        videopreview = (ImageView)findViewById(R.id.videopreview);
        videopreview.setVisibility(View.GONE);
        play = (ImageView)findViewById(R.id.play);
        play.setVisibility(View.GONE);
        imgView = (ImageView) findViewById(R.id.imgView);
     //   preview = (ImageView)findViewById(R.id.privew);
        imgView.setVisibility(View.GONE);
        deleteimage = (ImageView) findViewById(R.id.deleteimage);
        deleteimage.setVisibility(View.GONE);
        videoView = (VideoView)findViewById(R.id.video);
    /*   pDialog = new ProgressDialog(createnewnote.this);
        // Set progressbar title
        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();
        //  videoView = (VideoView)findViewById(R.id.video);
      MediaController vidControl = new MediaController(this);
        videoView = (VideoView)findViewById(R.id.video);
        vidControl.setAnchorView(videoView);
        videoView.setMediaController(vidControl);
        //fileUri.getPath()
        Uri videoUri =Uri.parse(VideoURL); ;//data.getData();
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoView.start();
            }
        });*/

        setupToolbar();

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


    @Override
    public void onBackPressed() {
            moveTaskToBack(isTaskRoot());
            finish();

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
        getMenuInflater().inflate(R.menu.menu_createnewnote, menu);
        // Log.e(" onCreateO)"," onCreateOp)");
        return true;
        // return super.onCreateOptionsMenu(menu);
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

    public void save(MenuItem item)  throws IndexOutOfBoundsException{
        try {
            try {

                String timedata = "0";
                String monthname = MONTHS[momth];
                String time = monthname + " " + day + "," + " " + year;
                title = edittitle.getText().toString();
                note = editnote.getText().toString();
                sql = database1.getWritableDatabase();
                if ((!title.isEmpty()) && (!note.isEmpty())) {

                    if (video==1)
                    {
                        String videoinstring = String.valueOf(video);
                        database1.insertvideo(sql, videoinstring, intent,img,time,title,note);
                    }

                    else {
                        database1.insert(title, note, sql, timedata, time, img, imgDecodableString, intent);

                        // String hello = "hello";
                        //database1.table2(hello, "1", sql);
                        Toast.makeText(getBaseContext(), "note  created", Toast.LENGTH_SHORT).show();
                        float mbTotal = megabytesAvailable();
                        final String totAvailable = "total MB available: " + mbTotal;
                        Log.e("create", "ory total" + totAvailable);
                        float mbFree = megabytesFree();
                        final String totFree = "free MB available: " + mbFree;
                        Log.e("create", "in mb" + totFree);
                        save = 1;
                    }

                } else {

                    Toast.makeText(getBaseContext(), "Enter  both", Toast.LENGTH_SHORT).show();
                }
            }
            catch (OutOfMemoryError i)
            {

            }
        } catch (Exception e) {
        }
    }



    public  float megabytesFree() {
        float mbUsed = 0;
        try {


            Runtime rt = Runtime.getRuntime();
            float bytesUsed = rt.totalMemory();
            mbUsed = bytesUsed / BYTES_IN_MB;
            float mbFree = megabytesAvailable() - mbUsed;
            return mbFree;
        }
        catch (Exception e){

        }
        return mbUsed;
    }

    public float megabytesAvailable() {

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




    public void back(View view) {

            moveTaskToBack(isTaskRoot());
            finish();

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
        Log.e("request", "is" + requestCode);
        Log.e("request", "code" + resultCode);
        // try {

        // try {


        // When an Image is picked
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data
              video = 0;
            imgView.setVisibility(View.VISIBLE);
            videopreview.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            deleteimage.setVisibility(View.GONE);
            intent = data.toUri(0);
            Log.e("ui", "is" + intent);
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);

            cursor.close();
            // Set the Image in ImageView after decoding the String

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            Bitmap b = BitmapFactory.decodeFile(imgDecodableString);
            float imagetype = readBitmapInfo(b);
            final float mbTotal = megabytesAvailable();
            final String totAvailable = "total MB available: " + mbTotal;
            Log.e("main", "ory total" + totAvailable);
            final float mbFree = megabytesFree();
            final String totFree = "free MB available: " + mbFree;
            Log.e("main", "in mb" + totFree);

            resizedBitmap = resizeimage(mbTotal, b, imagetype);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            readBitmapInfo(resizedBitmap);


            imgView.setImageBitmap(resizedBitmap);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();
            Toast.makeText(this, "image is successfully inserted",
                    Toast.LENGTH_LONG).show();


        } else if (requestCode == 2) {
            Log.e("enter", "into camera");
            Log.e("camera", "picture" + fileUri);
            videopreview.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            deleteimage.setVisibility(View.GONE);
              video = 0;
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(fileUri.getPath());
            if (yourSelectedImage != null) {
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
                imgView.setVisibility(View.VISIBLE);

                imgView.setImageBitmap(resizedBitmap);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                img = bos.toByteArray();
                Toast.makeText(this, "camera take image ",
                        Toast.LENGTH_LONG).show();
                if (image.exists()) {
                    image.delete();
                    //   Toast.makeText(this, "file completely deleted",
                    // Toast.LENGTH_LONG).show();
                }
            }
            if (image.exists()) {
                image.delete();
                // Toast.makeText(this, "file completely deleted",
                //       Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == 3) {
             video = 1;
             Uri videoUri = data.getData();
             imgView.setVisibility(View.GONE);
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
         } else if (requestCode == 4) {

             video = 1 ;
             Uri videoUri = data.getData();
             imgView.setVisibility(View.GONE);
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
             Toast.makeText(this, "Videeo successfully inserted", Toast.LENGTH_LONG).show();

         }


         else

        {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
           /* }
            catch (OutOfMemoryError i)
            {

            }
}


        catch (Exception e)
        {

        }*/

    }

    public void playvideo(View view) {

        Intent intent1 = new Intent(createnewnote.this,playvideo.class);
        intent1.putExtra("videouri",intent);
        intent1.putExtra("activity","fetchvideo");
        startActivity(intent1);

    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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




    private File createImageFilevideo() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "image file" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".3gp",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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



    public void cancel(MenuItem item) {

            moveTaskToBack(isTaskRoot());
            finish();


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
            final CharSequence[] items = {"Take Photo", "Choose from Library","make video","Choose video", "cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(createnewnote.this);
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




    public void deleteimage(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(createnewnote.this);

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
                imgView.setVisibility(View.GONE);
                img = null;
                imgDecodableString = null;
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


    public void deletevideo(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(createnewnote.this);

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

