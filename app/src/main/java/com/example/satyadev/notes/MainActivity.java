package com.example.satyadev.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.satay.dev.example.notes.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

//<action android:name="android.intent.action.QUICKBOOT_POWERON" />
public class MainActivity extends AppCompatActivity {

    int id,videocount;
    ListView list;
    int checkupdate = 0;
    Context context;
    String time1, cancel;
    SQLiteDatabase sql;
    data database1;
    Intent intent;
    String check;
    Cursor cursor;
    int limit = 0;
    ImageView image;
    String imageepath;
    listdataadapter lda;
    GetItems getItems;
    private SwipeRefreshLayout swipeRefreshLayout;
    public  ArrayList<dataprovider> CustomListViewValuesArr = new ArrayList<dataprovider>();
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private byte[] img = null;
    private byte[] preview = null;
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;



    /** this is the inner class is used Asytask in these only doinbackground method is only worker thread and all other
 * methods are main thread means worker thread not intract with UI methods*/
    class GetItems extends AsyncTask<Void, Void,Void> {

        boolean  finished = false;
        @Override
        protected void onPreExecute() {

        }
/**  this doinbackground method is called publishprogress method and that method calls onprogressupdate method*/
        @Override
        protected Void doInBackground(Void... params) {

            while (!finished) {

                try {
                    if (isCancelled()) {
                        finished = true;
                    }
                    Thread.sleep(30000);
                    publishProgress();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... hello) {
            startshow();

        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);

/**  database1 is the object of data class  */
        database1 = new data(MainActivity.this);

/** This line is used make the data class object get the writable means able to write in database */
        sql = database1.getWritableDatabase();
        sql = database1.getReadableDatabase();
/** show(v) call show method to display data & setuptoolbat() o set custom toolbar. */
        startshow();

/** in these lines getItems object call execute method of the getitems inner class*/

        if (getItems==null) {
            getItems = new GetItems();
       //  getItems.execute();


        }
        setupToolbar();

/**swipreRefreshlayout is used to refresh layout when screen is pull down then this method is called*/

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
                                                        startshow();
                                                        swipeRefreshLayout.setRefreshing(false);

                                                    }
                                                }

        );

    }

/** onDestroy method is used to control what is destroy when the activity is destroy*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

/** this method is used to avoid recreation of activity when screen is rotated */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){


        }
    }


/** this method is used to calculate amount of memory free at runtime*/
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


/**this method is used to find total amount of memory availabe*/
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







    /**
     * This method is called when user pressed phone back button.
     */
    @Override
    public void onBackPressed() {
        getItems.cancel(true);
        finish();
        moveTaskToBack(true);


    }


    /**
     * This method is used to show custom toolbar.
     */
    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * This method is  called for create  menu options.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    /*/ This method is responsible for action toolbar items click. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button, so long
         // as you specify a parent activity in AndroidManifest.xml. */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


/**this method is used to find out that listadapter is created or not if adapter is not created then it create
 * it and if it is created then it is not created*/
    public void startshow()
    {
       try {

            try {
        Log.e("start show","into startshow");

                if (list.getAdapter() == null) {
                    lda = new listdataadapter(getApplicationContext(), R.layout.activity_main);
                    show();
                    Log.e("enteer","into startshow");
                    /**inertfirst is the listdataadadapetr method which is used to assign customlistviewvaluearr valuees to list
                     * in listadataadapeter*/
                    lda.insertfirst(CustomListViewValuesArr);
                    list.setAdapter(lda);
                    checkupdate = 1;



                }
                else {
/**this insert method od listadataadapter is used to delete data of list of listdataadapter*/
                    lda.insert();
                    Log.e("int the", "into startshow");
                    show();

                }
            } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError o)
        {

        }

    }

    /**
     * This method is used to show data of user entery in form of lists.
     */

    public void show() {

        try {

            try {
        Log.e("enteer","into show only");

/** This line is used make the data class object get the writable means able to read from database  */

/** getdata(sql) method is used to fetch data from database. */
                cursor = database1.getdatalimit(sql);

                if (cursor != null) {
                    Log.e("enteer", "into sonly");
                    if (cursor.moveToFirst()) {
String video = " ";
                        Log.e("cursor","into show only");
/** variable alarm is used to identitfy that alarm is set or not for that note. */
                        String alarm = " ";
                        String intt = " ";
                        do {
                            Log.e("cursor","into show ");
/** id variable is used to store id of individual user's notes. */
                            id = cursor.getInt(2);

/** time1 variable is used to indicate that when its value '0' means no notification is issue for this
 and when it is '1' means notification is issue for this note.*/
                            time1 = cursor.getString(3);
                            imageepath = null;
                            imageepath = cursor.getString(7);
/** date variable is used to store data of creation of each note. */
                            String date = cursor.getString(4);
                            intt = cursor.getString(8);
                            Log.e("intt","value"+intt);
/** id2 store string value of varibale id. */
                            String id2 = String.valueOf(id);

                            video = cursor.getString(9);
                            Log.e("value","of vakd "+video);
                            if (video!=null) {
                                 videocount = Integer.parseInt(video);
                                Log.e("count video","of vakd "+videocount);
                            }
                            else
                            {
                                videocount = 0;
                            }
/** name variable is used to store title of note.  */
                            String name = cursor.getString(0);

/** pass variable is used to store message or description of note. */
                            String pass = cursor.getString(1);

/** cancel variable is used to store unique id of every alarm set which is used to delete notification
 from notification table.*/
                            Log.e("is on","alarm ");
                            cancel = cursor.getString(5);
                            alarm = " ";
                            img = null;
/**this variable img is used to store image object*/
                            img = cursor.getBlob(cursor.getColumnIndex("image"));
                            preview = null;
/**this variable img is used to store image object*/
                            preview = cursor.getBlob(cursor.getColumnIndex("preview"));



/** getdetaildataalarm(sql,id2) is used to  check that particular note is in table3 or not if note is
 in table3 that means alarm is set for that note */
                            Cursor cursor4 = database1.getdetaildataalarm(sql, id2);
                            if (cursor4 != null) {

                                int t = 0;
                                if (cursor4.moveToFirst()) {

                                    String cancelalarm = cursor4.getString(1);
                                    String year = cursor4.getString(2);
                                    String month = cursor4.getString(3);
                                    String day = cursor4.getString(4);
                                    String hour = cursor4.getString(5);
                                    String minute = cursor4.getString(6);

                                    int year1 = Integer.parseInt(year);
                                    int month1 = Integer.parseInt(month);
                                    int day1 = Integer.parseInt(day);
                                    int hour1 = Integer.parseInt(hour);
                                    int minute1 = Integer.parseInt(minute);


                                    GregorianCalendar calendar = new GregorianCalendar(year1, month1, day1, hour1, minute1);

                                    String id3 = cursor4.getString(0);

                                    Calendar calendar2 = Calendar.getInstance();

                                    t = calendar.compareTo(calendar2);

                                    if (t == 1) {


                                        if (id3.equals(id2)) {

                                            alarm = "reminder";

                                        } else {

                                            alarm = " ";
                                        }
                                    } else {
                                        database1.deletenotificationalarm(sql, cancelalarm);
                                    }
                                }
                            }

                            Log.e("main activity","is"+date+"video"+videocount+"img"+preview);
/** call the constructor of dataprovider class to  set and get the data  of each note seprately */
                            dataprovider dp = new dataprovider(name, pass, id, time1, date, alarm, cancel, img,videocount,intt,preview);


/** call the add method listdataapapter class to  set each note data in listform. */
                            CustomListViewValuesArr.add(dp);
                            lda.notifyDataSetChanged();


                        }
                        while (cursor.moveToNext());
                    }


/** this is  used to click each item  of listview  */
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view, int position, long id1) {


/** This is  used to get the data of each note from  dataprovider class. */
                            dataprovider dp1 = (dataprovider) list.getItemAtPosition(position);

/** variable name2 is used to store the id of the note. */
                            int name2 = dp1.getId();
                            int countvideo = dp1.getVideo();

/** upadatenotificationumberdelete(sql,id2,time1); is used to  update table 'tablename2 with the variable time1'    */
                           // if (countvideo != 1) {
                                intent = new Intent(MainActivity.this, details.class);
                                check = "1";
                          //  } else if (countvideo == 1) {
                             //   intent = new Intent(MainActivity.this, showfetchvideo.class);
                               // check = "mainactivity";
                                Log.e("ready","to seee video"+videocount);
                           // }
/** pass two variables from that class to details class variable check is used to determine in class detail
 that  it is comes from mainactivity class  and variable name2 is passed in the form of string which is
 the unique id of every note  */

                            GetItems getItems = new GetItems();
                            getItems.cancel(true);
                            intent.putExtra("id2", String.valueOf(countvideo));
                            intent.putExtra("id", String.valueOf(name2));
                            startActivity(intent);


                        }
                    });
                }
           } catch (Exception ee) {

            }
        }
        catch (OutOfMemoryError oo)
        {

        }


    }
//    }

    /** This method is used to  handle the click event  of fab and used to create new note from createnewnote
     class */

    public void fab(View view) {

        Intent intent = new Intent(MainActivity.this, createnewnote.class);
        startActivity(intent);

    }

    public  void alarm(View view)
    {
        Intent intent = new Intent(MainActivity.this, alarmmanager.class);
        startActivity(intent);

    }

    /**   This method is used to move to alarmanager class to get detail about set alarms  */
    public void search(MenuItem item) {

        Intent intent = new Intent(MainActivity.this, search.class);
        startActivity(intent);

    }


    /** This method is used to move to notification class to get detail about notifications. */
    public void notification(View view) {

        Intent intent = new Intent(MainActivity.this, notification.class);
        startActivity(intent);

    }

    public void createnote(View view)
    {
        Intent intent = new Intent(MainActivity.this, createnewnote.class);
        startActivity(intent);
    }


    public void capturevideo(View view)
    {
        Intent intent = new Intent(MainActivity.this, fetchvideo.class);
        startActivity(intent);
    }
}



