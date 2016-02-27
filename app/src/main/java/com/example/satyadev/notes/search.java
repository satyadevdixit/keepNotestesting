package com.example.satyadev.notes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.satay.dev.example.notes.R;



public class search extends AppCompatActivity {

    int id,videocount;
    View v;
    ListView list;
    Context context;
    SQLiteDatabase sql;
    data database1;
    String name, pass, date, time, time1;
    Cursor cursor, cursor1, cursor2;
    String tname, id2, check;
    searchadapter searchadapter;
    ImageView image;
    Intent intent;
    EditText searchtext;
    SearchView searchView;
    TextView noresult;
    String search;
    private byte[] img = null;
    private byte[] preview = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        database1 = new data(search.this);
        list = (ListView) findViewById(R.id.notification);
        sql = database1.getWritableDatabase();
        // image = (ImageView) findViewById(R.id.imgView);
        searchtext = (EditText) findViewById(R.id.search);

        noresult = (TextView) findViewById(R.id.noresult);
        setupToolbar();
        show(v);
        //  noresult.setVisibility(View.GONE);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //        noresult.setVisibility(View.GONE);
                search = searchtext.getText().toString();
//if (search==null)
//{
//cursor = null;
//}
                noresult.setVisibility(View.VISIBLE);

                show(v);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //  searchView = (SearchView) findViewById(R.id.searchView);
    }

    public void cearedittext(View v) {
        searchtext.setText("");
    }

    public void back(View view) {

        moveTaskToBack(isTaskRoot());
        finish();

    }


    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * This method is  called for create  menu options.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

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


    /**
     * Whhen phone back button is pressed that method is called
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(isTaskRoot());
        finish();

    }


    public void show(View view) {

       try {
            try {
                searchadapter = new searchadapter(getApplicationContext(), R.layout.activity_search);
                list.setAdapter(searchadapter);
                sql = database1.getReadableDatabase();
               search = searchtext.getText().toString();//searchView.getQuery().toString();
                Log.e("querry", "is" + search);
      //  cursor = null;
/** getnotificationdataalarm() this method retruns all the entreis of the notification table 'tablename4' */
                cursor = database1.searchresult(sql, search);
                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        String video = " ";
                        do {

                            Log.e("not null","is not"+cursor);
/** variable id store the id of that notes whose alarm is set */
                            String id = cursor.getString(2);

                            video = cursor.getString(4);
                            Log.e("value","of vakd "+video);
                            videocount = 0;
                            if (video!=null) {
                                videocount = Integer.parseInt(video);
                                Log.e("count video","of vakd "+videocount);
                            }

                            preview = null;
/**this variable img is used to store image object*/
                            preview = cursor.getBlob(cursor.getColumnIndex("preview"));

/**  variable date and time store alarm  set date and time respectively */
                            name = cursor.getString(0);
                            pass = cursor.getString(1);
                            img = null;
                            img = cursor.getBlob(cursor.getColumnIndex("image"));
                            if (pass != null) {
                                noresult.setVisibility(View.GONE);
                            }
/** get name of the table */


/** call the constructor of notificationdataprovider class to  set and get the data  of each note seprately */
                            searchdataprovider dp = new searchdataprovider(name, pass, id, img,videocount,preview);
                            searchadapter.add(dp);

                            //   Log.e("ookkkk", "done"+name);


                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View view, int position, long id1) {

/** This is  used to get the data of each note from  notificationdataprovider class. */
                                    searchdataprovider searchdataprovider = (searchdataprovider) list.getItemAtPosition(position);

/** variable name 2 store id of that note on which the user click */
                                    String name2 = searchdataprovider.getId();

/** variable time2 stores indiactore of that note */

                                    int countvideo = searchdataprovider.getVideo();

/** upadatenotificationumberdelete(sql,id2,time1); is used to  update table 'tablename2 with the variable time1'    */
                                    if (countvideo!= 1) {
                                        Log.e("seach","activity"+countvideo);
                                        intent = new Intent(search.this, details.class);
                                        check = "mainactivity";
                                    } else if (countvideo == 1) {
                                        intent = new Intent(search.this, showfetchvideo.class);
                                        check = "mainactivity";
                                        Log.e("ready","to seee video"+videocount);
                                    }

/** This check variable indicates that move to detail class is from notification class
 because this helps in move back to notification class when user pressed phone back button  */
                                    String check = "mainactivity";
                                    intent.putExtra("id2", check);
                                    intent.putExtra("id", String.valueOf(name2));
                                   // Log.e("ookkkk", "done" + name2);
                                    startActivity(intent);

                                }
                            });


                        }
                        while (cursor.moveToNext());

                    }
                } else {
                    noresult.setVisibility(View.VISIBLE);

                }

           } catch (Exception e) {
                System.gc();
            }
        }
        catch (OutOfMemoryError o) {
        }
    }

    public void clearedittext(MenuItem item) {
        searchtext.setText("");
    }
}