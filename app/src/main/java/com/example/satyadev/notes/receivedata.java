package com.example.satyadev.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;

import com.satay.dev.example.notes.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by satyadev on 04-11-2015.
 */
public class receivedata extends AppCompatActivity {

    class fetchdata extends AsyncTask<Void, Void,Void> {
        String suc = "success";
        JSONArray jsonArrayforfetch;
        byte[] img1 = null;

        // String pid;
        boolean  finished = false;
        @Override
        protected void onPreExecute() {
            //  MainActivity mainActivity = new MainActivity();
            //  img1 =   mainActivity.img;
            img1 = img;
        }
        /**  this doinbackground method is called publishprogress method and that method calls onprogressupdate method*/
        @Override
        protected Void doInBackground(Void... params) {


            try {


                URL url1 = null;

                url1 = new URL(link);

                image1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

                Log.e("success", "response" +image1+link);
                publishProgress();
            }
            catch (Exception i)
            {
                Log.e("error","is that"+i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... hello) {
            Log.e("bitmap", "that" + image1);
            showfetchdata();

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }



    EditText edittitle, editnote;
    int year, day, momth;
    data database1;
    ImageView imgView,deleteimage;
    SQLiteDatabase sql;
    String title = null, note = null;
Bitmap b,image1;
    Bitmap resizedBitmap;
    String imgDecodableString;
    private byte[] img = null;
    private Uri uriContact;
    private String contactID;
    String intt;
    String link;

    int save =0;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivedata);
        database1 = new data(receivedata.this);
        sql = database1.getWritableDatabase();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edittitle = (EditText) findViewById(R.id.title);
        editnote = (EditText) findViewById(R.id.note);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        momth = calendar.get(Calendar.MONTH);
        imgView = (ImageView) findViewById(R.id.imgView);
        imgView.setVisibility(View.GONE);

        deleteimage = (ImageView) findViewById(R.id.deleteimage);
        deleteimage.setVisibility(View.GONE);

        setupToolbar();

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {

    handlesendtextandimage(intent);


        }
         else {
            // Handle other intents, such as being started from the home screen
        }



    }

    public void showfetchdata()
    {
        imgView.setVisibility(View.VISIBLE);
        imgView.setImageBitmap(image1);
        Log.e("great","get image"+image1);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        if (image1!=null) {
            image1.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            img = bos.toByteArray();

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



/**this method is used to retrive data from intent to which other apps can share information*/
    void handlesendtextandimage(Intent intent1) throws OutOfMemoryError{

try
{
/**in this line text is access from intent*/
    String sharedText = intent1.getStringExtra(Intent.EXTRA_TEXT);
/**subject is access of the information share*/
    String subject = intent1.getStringExtra(Intent.EXTRA_SUBJECT);

    if (sharedText != null) {
        edittitle.setText(subject, TextView.BufferType.EDITABLE);
        editnote.setText(sharedText, TextView.BufferType.EDITABLE);
        editnote.setMovementMethod(LinkMovementMethod.getInstance());
        editnote.setText(sharedText, TextView.BufferType.EDITABLE);
               fetchdata fetchdata = new fetchdata();
                if(Linkify.addLinks(editnote, Linkify.ALL))
                {
                    link = editnote.getText().toString();
                    fetchdata.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    Log.e("text is","hyperlink"+link);
                }
        editnote.setMovementMethod(LinkMovementMethod.getInstance());
        // Update UI to reflect text being shared
    }

    Uri imageUri = intent1.getParcelableExtra(Intent.EXTRA_STREAM);

    if (imageUri != null) {
/**in this line image dta is convert into string form*/
        intt = imageUri.toString();
/**in this line path of te image is access*/
        imgDecodableString = imageUri.getPath();
        if (imageUri != null) {
            imgView.setVisibility(View.VISIBLE);

        }


        try {
/** in these lines image is access from intent to the location whre image is store from the app which share image*/
            InputStream image_stream = getContentResolver().openInputStream(imageUri);
            b = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            Log.e("heeelo","image"+image_stream);
            if (b==null)
            {

            }

        } catch (IOException i) {
            Log.e("not done ", "problem" + i);
        }


// for API 19 or above

       /*     String filePath = "";
            String wholeID = DocumentsContract.getDocumentId(imageUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getBaseContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();


//Log.e("jwddjdjkwjkd","dwsdjkjjd"+b);
        String[] proj = {MediaStore.Images.Media.DATA};


        try {


            CursorLoader cursorLoader = new CursorLoader(
                    getBaseContext(),
                    imageUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();
                if (imgDecodableString == null) {
                    imgDecodableString = cursor.getString(column_index);
                }
                Log.e("decode", "image path" + imgDecodableString);
                Log.e("decode", "column" + column_index);
                Log.e("decode", "cursor" + cursor);
            }
        } catch (Exception c) {

        }
*/

        try {

            try {

                float imagetype = readBitmapInfo(b);
                if (b != null) {

                    final float mbTotal = megabytesAvailable();
                    final String totAvailable = "total MB available: " + mbTotal;
                    Log.e("main", "ory total" + totAvailable);
                    final float mbFree = megabytesFree();
                    final String totFree = "free MB available: " + mbFree;
                    Log.e("main", "in mb" + totFree);

                    if ((mbTotal >= 96.0) && (mbTotal <= 120.0)) {

                        if ((imagetype >= 9.0) && (imagetype < 10.0)) {
/** in this line image is resized according to given width and height*/
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


                    int imagesize = resizedBitmap.getByteCount();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    Log.e("after", "resize" + imagesize);
                  //  readBitmapInfo(resizedBitmap);
                    imgView.setImageBitmap(resizedBitmap);
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    img = bos.toByteArray();

                    Log.e("object", "img" + img);
                }
            } catch (Exception e) {

            }
        } catch (IndexOutOfBoundsException i) {

        }

    }
}
catch (Exception i)
{

}
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
        getMenuInflater().inflate(R.menu.menu_receivedata, menu);
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

    public void save(MenuItem item) throws OutOfMemoryError, Exception , IndexOutOfBoundsException{

        SQLiteDatabase db1;
        String timedata = "0";
        String monthname = MONTHS[momth];
        String time = monthname + " " + day + "," + " " + year;

        title = edittitle.getText().toString();
        note = editnote.getText().toString();
        if ((!title.isEmpty()) && (!note.isEmpty())) {

            sql = database1.getWritableDatabase();
            database1.insert(title, note, sql, timedata, time, img,imgDecodableString,intt);

           // String hello = "hello";
           // database1.table2(hello,"1",sql);
            Toast.makeText(getBaseContext(), "note  created", Toast.LENGTH_SHORT).show();
            float mbTotal = megabytesAvailable();
            final String totAvailable = "total MB available: "+mbTotal;
            Log.e("create", "ory total" + totAvailable);
            float mbFree = megabytesFree();
            final String totFree = "free MB available: "+mbFree;
            Log.e("create", "in mb" + totFree);
            save = 1;

        } else {

            Toast.makeText(getBaseContext(), "Enter  both", Toast.LENGTH_SHORT).show();
        }

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







    private float readBitmapInfo(Bitmap bm) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        float BYTES_PER_PX = 4.0f; //32 bit


        float imageHeight = bm.getHeight();
        float imageWidth = bm.getWidth();
        String imageMimeType = options.outMimeType;
        float estimatedimagesize = imageWidth * imageHeight * BYTES_PER_PX/BYTES_IN_MB;
        Log.e("dlfkf", "w,h, type:" + imageWidth + ", " + imageHeight + ", " + imageMimeType);
        Log.e("dkslkk", "estimated memory required in MB: " + imageWidth * imageHeight * BYTES_PER_PX / BYTES_IN_MB);
        return estimatedimagesize;

    }



    public void cancel(MenuItem item) {
        if (save>0) {
            Intent intent = new Intent(receivedata.this, MainActivity.class);

           startActivity(intent);
          //  moveTaskToBack(isTaskRoot());
            finish();
        }
        else
        {
            moveTaskToBack(isTaskRoot());
        }
    }

    public void back(View view) {
        if (save>0) {
          //  Intent intent = new Intent(receivedata.this, MainActivity.class);
            //startActivity(intent);
            moveTaskToBack(isTaskRoot());
            finish();
        }
        else
        {
            moveTaskToBack(isTaskRoot());
            finish();
        }

    }



    public void deleteimage(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(receivedata.this);

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


}



