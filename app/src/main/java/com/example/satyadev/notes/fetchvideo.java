package com.example.satyadev.notes;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.satay.dev.example.notes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class fetchvideo extends AppCompatActivity {

    EditText edittitle, editnote;
    int year, day, momth;
    private int RESULT_LOAD_VID = 4;
    private int REQUEST_VIDEO_CAPTURE = 3;
    ImageView imageView,deleteimage;
    data database1;
    String intent;
    SQLiteDatabase sql;
    private static byte[] img;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchvideo);
        database1 = new data(fetchvideo.this);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        momth = calendar.get(Calendar.MONTH);
        imageView = (ImageView)findViewById(R.id.imgView);
        imageView.setVisibility(View.GONE);
        edittitle = (EditText) findViewById(R.id.title);
        editnote = (EditText) findViewById(R.id.note);
        deleteimage = (ImageView) findViewById(R.id.play);
        deleteimage.setVisibility(View.GONE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setupToolbar();

    }

    public void cancel(MenuItem item) {

        moveTaskToBack(isTaskRoot());
        finish();


    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(isTaskRoot());
        finish();

    }


    public void back(View view) {

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
        getMenuInflater().inflate(R.menu.menu_fetchvideo, menu);
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



    public void save(MenuItem item)  throws IndexOutOfBoundsException{
        try {
            try {

                String timedata = "0";
                String monthname = MONTHS[momth];
                String time = monthname + " " + day + "," + " " + year;
              String  title = edittitle.getText().toString();
              String  note = editnote.getText().toString();
        Log.e("save video","intent"+intent);
                if (!intent.isEmpty()) {
String video = "1";
                    sql = database1.getWritableDatabase();
                    database1.insertvideo(sql, video, intent,img,time,title,note);
                    Log.e("save video", "done");
                    Toast.makeText(getBaseContext(), "Video saved", Toast.LENGTH_SHORT).show();
                }


                 else {

                    Toast.makeText(getBaseContext(), "No Video Found", Toast.LENGTH_SHORT).show();
                }
            }
            catch (OutOfMemoryError i)
            {

            }
        } catch (Exception e) {
        }
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
                 Uri videoUri = data.getData();
                 intent = data.toUri(0);
                 String path =  getRealPathFromURI(videoUri);
                 Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                 Log.e("bitmap", "is that" + bitmap + "path" + path);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                 imageView.setVisibility(View.VISIBLE);
                 imageView.setImageBitmap(bitmap);
                 deleteimage.setVisibility(View.VISIBLE);
                 img = bos.toByteArray();
                 //  playvideo(videoUri);
             } else if (requestCode == 4) {
                 Uri videoUri = data.getData();
                 intent = data.toUri(0);
                 String path =  getRealPathFromURI(videoUri);
                 Bitmap bitmap =  ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                 Log.e("bitmap", "is that" + bitmap + "path" + path);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                 imageView.setVisibility(View.VISIBLE);
                 imageView.setImageBitmap(bitmap);
                 deleteimage.setVisibility(View.VISIBLE);
                 img = bos.toByteArray();
                 //  playvideo(videoUri);
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




    public void camera(MenuItem item) {

        try {
            final CharSequence[] items = { "make video","choose video","cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(fetchvideo.this);
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
                        //Start the Intent


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

    public void playvideo(View view) {

       Intent intent1 = new Intent(fetchvideo.this,playvideo.class);
        intent1.putExtra("videouri",intent);
        intent1.putExtra("activity","fetchvideo");
        startActivity(intent1);

    }
}
