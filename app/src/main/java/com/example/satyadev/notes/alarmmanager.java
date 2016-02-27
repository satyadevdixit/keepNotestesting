package com.example.satyadev.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class alarmmanager  extends AppCompatActivity {

private  AlarmManagerBroadcast alarm;
    int id;
    View v;
    ListView list;
    String time2,datetime;
    int videocount = 0;
    Context context;
    SQLiteDatabase sql;
    data database1;
    String name,pass,date,time,time1;
    Cursor cursor,cursor1,cursor2;
    String tname,id2,alarmtime,cancel,name2;
    int canceltime;
    int sign = 2;
 alarmmanageradapter alarmmanageradapter;
    private  byte[] img = null;
    private  byte[] preview = null;
    private SwipeRefreshLayout swipeRefreshLayout;
ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmmanager);

        image = (ImageView) findViewById(R.id.imgView);
        database1 = new data(alarmmanager.this);
        list = (ListView) findViewById(R.id.notification);
        sql = database1.getWritableDatabase();
        alarm = new AlarmManagerBroadcast();
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


    @Override
    public void onBackPressed() {

          moveTaskToBack(isTaskRoot());
            finish();
        }


    public void back(View view) {
        moveTaskToBack(isTaskRoot());
        finish();
    }


    public void show(View view) {

     //try {


            alarmmanageradapter = new alarmmanageradapter(getApplicationContext(), R.layout.activity_alarmmanager);

            list.setAdapter(alarmmanageradapter);

            sql = database1.getReadableDatabase();

/** This method returns entry of all set alarms */
            cursor = database1.getdataalarm(sql);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {

/** variable id, date, time, alarmtime, cancel, datetime are id of that note, set  date and time of that alarm, alarmtime is at what
 time alarm is set, cancel is the id of alarm */
                        String id = cursor.getString(0);
                        date = cursor.getString(2);
                        time = cursor.getString(1);
                        alarmtime = cursor.getString(3);
                        cancel = cursor.getString(4);
                        canceltime = Integer.parseInt(cancel);
                        datetime = cursor.getString(5);
                        String tname2 = database1.getTablename();

/** get detail of alarm from table 'tablename' */
                        cursor1 = database1.getnotificationdetaildata1(sql, id);
                        if (cursor1.moveToFirst()) {
                            String video = " ";
                            videocount = 0;
                            name = cursor1.getString(0);
                            pass = cursor1.getString(1);
                            time1 = cursor1.getString(3);
                            video = cursor1.getString(5);
                            preview = null;
                            if (video!=null)
                            {
                                Log.e("in alarm","manager"+video);
                                videocount = Integer.parseInt(video);
                                preview = cursor1.getBlob(cursor1.getColumnIndex("preview"));

                            }

                            img = null;
                            img = cursor1.getBlob(cursor1.getColumnIndex("image"));
                            image = (ImageView) findViewById(R.id.imgView);

                        }

                        alarmmanagerdataprovider alarmmanagerdataprovider = new alarmmanagerdataprovider(name, pass, id, time, date, time1, alarmtime, cancel, datetime, img,videocount,preview);


                        alarmmanageradapter.add(alarmmanagerdataprovider);

                    }
                    while (cursor.moveToNext());
                }

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id1) {

                        alarmmanagerdataprovider alarmmanagerdataprovider1 = (alarmmanagerdataprovider) list.getItemAtPosition(position);

                        name2 = alarmmanagerdataprovider1.getCancel();

                        canceltime = Integer.parseInt(name2);
                        Bundle args = new Bundle();
                        args.putString("name", name2);
                        alertdialog alertdialog = new alertdialog();
                        alertdialog.setArguments(args);
                        alertdialog.show(getFragmentManager(), "my");


                    }

                });

            }


       // }
       // catch (Exception e)
      //  {
          //  System.gc();
       // }
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
        Intent intent = new Intent(alarmmanager.this, MainActivity.class);
        startActivity(intent);
    }


}
