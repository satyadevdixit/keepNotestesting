package com.example.satyadev.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.satay.dev.example.notes.R;

public class playvideo extends AppCompatActivity {

    VideoView videoView;
    SQLiteDatabase sql;
    String id,intent;
    Uri uri;
    data database1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);
        database1 = new data(playvideo.this);
        sql = database1.getReadableDatabase();
        sql = database1.getWritableDatabase();
        String activity = getIntent().getStringExtra("activity");
        String checkfetch = "fetchvideo";
        String checkshow = "showvideo";
        if (activity.equals(checkfetch)) {
            id = getIntent().getStringExtra("videouri");
            try {
                Intent intent1 = Intent.parseUri(id, 0);
                uri = intent1.getData();
                playvideo(uri);
            } catch (Exception e) {
            }
        }
        else if (activity.equals(checkshow))
        {
            id = getIntent().getStringExtra("videouri");
            showfetchvideo();
        }

    }


    public void back(View view) {

        moveTaskToBack(isTaskRoot());
        finish();

    }

    public void showfetchvideo()
    {

        Cursor cursor = database1.fetchplayvideo(sql, id);
        Log.e("show", "videoy o89o9 9" + cursor.getCount());
        if((cursor.getCount())>0) {
            if (cursor.moveToFirst()) {
                String vid = cursor.getString(1);
                intent = cursor.getString(0);
                try {

                    Log.e("show", "video" + intent + "hjdhjewhd" + vid);
                    Intent intent1 = Intent.parseUri(intent, 0);
                    uri = intent1.getData();
                    Log.e("show", "video" + uri + "hjdhjewhd" + intent1);
                    playvideo(uri);
                } catch (Exception h) {
                    Log.e("show fetch", "video error" + h);
                }
            }
        }
    }


    public void playvideo(Uri uri) {
        videoView = (VideoView) findViewById(R.id.video);
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(videoView);
        videoView.setMediaController(vidControl);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("camera", "videwoe");
                videoView.start();
            }
        });

    }
}
