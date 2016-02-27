package com.example.satyadev.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.LruCache;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.satay.dev.example.notes.R;
public class
        notification extends AppCompatActivity {

    int id;
    View v;
    ListView list;
    Context context;
    SQLiteDatabase sql;
    data database1;
    String name,pass,date,time,time1;
    Cursor cursor,cursor1,cursor2;
    String tname,id2,check;
    notificationadapter notificationadapter;
    ImageView image;
    private  byte[] img = null;
    LruCache lruCache;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

/** database1 is the object of the data class */
        database1 = new data(notification.this);
        list = (ListView) findViewById(R.id.notification);
        sql = database1.getWritableDatabase();
        image = (ImageView) findViewById(R.id.imgView);
/**  get the name of the table by calling that method */
        tname = database1.getTablename2();
        show(v);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setRefreshing(true);
       /* swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);


                                    }
                                }
        );*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
                                                        show(v);
                                                        swipeRefreshLayout.setRefreshing(false);
                                                        Log.e("pull", "refresh");
                                                    }
                                                }

        );
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


/** This method is used to move from notification class to Mainactivity class */
    public void back(View view) {

        moveTaskToBack(isTaskRoot());
        finish();

    }


/** Whhen phone back button is pressed that method is called */
    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(notification.this, MainActivity.class);
        startActivity(intent);
*/
        moveTaskToBack(isTaskRoot());
            finish();

    }


/** show the note in detail */
    public void show(View view) {

        try {


            notificationadapter = new notificationadapter(getApplicationContext(), R.layout.activity_notification);
            list.setAdapter(notificationadapter);
            sql = database1.getReadableDatabase();

/** getnotificationdataalarm() this method retruns all the entreis of the notification table 'tablename4' */
            cursor = database1.getnotificationdataalarm(sql);
            if (cursor != null) {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {

/** variable id store the id of that notes whose alarm is set */
                            String id = cursor.getString(0);

/**  variable date and time store alarm  set date and time respectively */
                            date = cursor.getString(2);
                            time = cursor.getString(1);

/** get name of the table */
                            String tname2 = database1.getTablename();

/** get alarm id used to delete entry from table 'tablename4' when user check the notification */
                            String cancel = cursor.getString(4);

/** This method is used to get the detail of that note whose id is pass in variable id from table
 'tablename' */
                            cursor1 = database1.getnotificationdetaildata1(sql, id);
                            if (cursor1.moveToFirst()) {

/** variable name , pass and time1 store title, message and indicator that notification is issue for
 that note */
                                name = cursor1.getString(0);
                                pass = cursor1.getString(1);
                                time1 = cursor1.getString(3);
                                img = null;
                                img = cursor1.getBlob(cursor1.getColumnIndex("image"));
                                image = (ImageView) findViewById(R.id.imgView);

/** call the constructor of notificationdataprovider class to  set and get the data  of each note seprately */
                                notificationdataprovider dp = new notificationdataprovider(name, pass, id, time, date, time1, cancel, img);

                                notificationadapter.add(dp);

                            }


                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View view, int position, long id1) {

/** This is  used to get the data of each note from  notificationdataprovider class. */
                                    notificationdataprovider dp1 = (notificationdataprovider) list.getItemAtPosition(position);

/** variable name 2 store id of that note on which the user click */
                                    String name2 = dp1.getId();

/** variable time2 stores indiactore of that note */
                                    String time2 = dp1.getTime1();

/** variable canceltime is used to store id of he alarm */
                                    String canceltime = dp1.getCancel();

                                    int x1 = Integer.parseInt(time2);

                                    database1.deletenotificationentry(sql, canceltime);

                         /*   if (x1 == 1) {

/** This method is used to deleteentry from table 'tablename4' on user click on that note */


/** This method is used to get the counting of set alarms */
                             /*   cursor2 = database1.getnotificationnumber(sql);

                                if (cursor2.moveToFirst()) {

/** This variable store number of set alarms */
                               /*     String notificationnumber = cursor2.getString(0);
                                    int x = Integer.parseInt(notificationnumber);
                                    x--;
                                    String newnumber = Integer.toString(x);

/** This method set newnumber counting of set alarms to table 'tablename5' */
                                  /*  database1.settnotificationnumber(sql, notificationnumber, newnumber);
                                }

                                String time1 = "0";

/** This method is used to set no notification is issue for that note on which the user click
    where name2 is id of that note in table 'tablename' */
                                /*database1.upadatenotificationumberdelete(sql, name2, time1);

                            }
*/
                                    Intent intent = new Intent(notification.this, details.class);

/** This check variable indicates that move to detail class is from notification class
 because this helps in move back to notification class when user pressed phone back button  */
                                    String check = "notification";
                                    intent.putExtra("id2", check);
                                    intent.putExtra("id", String.valueOf(name2));
                                    startActivity(intent);

                                }
                            });


                        }
                        while (cursor.moveToNext());

                    }
                }
            }
        }
        catch (Exception e)
        {
            System.gc();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
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

    public void next(MenuItem item) {
        Intent intent = new Intent(notification.this, MainActivity.class);
        startActivity(intent);
    }
}
