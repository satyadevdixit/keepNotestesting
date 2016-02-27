package com.example.satyadev.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.satay.dev.example.notes.R;

import java.util.List;

/**
 * Created by satyadev on 28-08-2015.
 */
public class listdataadapter extends ArrayAdapter {
    int currentposition ;
    List list ;
    View view;
    private  byte[] img = null,preview = null;
    String alarm1,intt;
    private   Bitmap b1;
    EventLog mEvents;

    float image;
    String imagepath;

    float BYTES_IN_MB = 1024.0f * 1024.0f;


    public listdataadapter(Context context, int resource) {
        super(context, resource);

    }


    class layouthandler
    {
        TextView name,pass,date,alarm;
        ImageView imageView,imgView,playvideo,play;
        VideoView videoView;
    }

    public void insert ()
    {

        try {


            list.clear();
            notifyDataSetChanged();

        }
        catch (Exception e)
        {

        }

    }


    public void insertfirst(List d)
    {
        try {
            list = d;
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void add(Object object) {
        super.add(object);

    }

    @Override
    public int getCount() {
        return list.size();

    }



    @Override
    public Object getItem(int position) {

        return list.get(position);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // MediaController vidControl1 = new MediaController(getContext());
        currentposition = position;
        View row =  convertView;
        layouthandler layouthandler;

      try {
            try {


                if (row == null) {

                    LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = layoutInflater.inflate(R.layout.show, parent, false);
                    layouthandler = new layouthandler();

                    layouthandler.date = (TextView) row.findViewById(R.id.date);
                    layouthandler.name = (TextView) row.findViewById(R.id.name);
                    layouthandler.pass = (TextView) row.findViewById(R.id.pass);
                    layouthandler.imageView = (ImageView) row.findViewById(R.id.alarm);
                    layouthandler.imgView = (ImageView) row.findViewById(R.id.imgView);
                    layouthandler.play = (ImageView) row.findViewById(R.id.play);
                    layouthandler.playvideo = (ImageView) row.findViewById(R.id.playvideo);
                    row.setTag(layouthandler);
                    Log.e("first step", "int that is" );

                } else {
                    layouthandler = (layouthandler) row.getTag();

                }

                layouthandler.play.setVisibility(View.GONE);
                layouthandler.imgView.setVisibility(View.GONE);
                layouthandler.imageView.setVisibility(View.GONE);
                layouthandler.playvideo.setVisibility(View.GONE);

                dataprovider dataprovider = (dataprovider) this.getItem(position);
        String name = null;
        name = dataprovider.getName();
                Log.e("second step", "int that is" );
        if (name==null)
        {
            layouthandler.name.setVisibility(View.GONE);
        }
        else {
            layouthandler.name.setText((dataprovider.getName()));
        }

        String pass = null;
        pass = dataprovider.getPass();
        if (pass==null)
        {
            layouthandler.pass.setVisibility(View.GONE);
        }
        else {
            layouthandler.pass.setText((dataprovider.getName()));
        }

                layouthandler.pass.setText(dataprovider.getPass());
                layouthandler.date.setText(dataprovider.getDate());

                Log.e("third step", "int that is");
                float mbTotal = megabytesAvailable();
                float mbFree = megabytesFree();
                int video = dataprovider.getVideo();
        Log.e("error","int that is"+video);
                img = null;
                img = dataprovider.getImg();
               preview = null;
               preview = dataprovider.getPreview();
                alarm1 = null;
                intt = null;
                alarm1 = dataprovider.getAlarm();
                if (alarm1 == "reminder") {

                    layouthandler.imageView.setVisibility(View.VISIBLE);

                }
                if ((img != null)&&(video==0)) {

                    layouthandler.imgView.setVisibility(View.VISIBLE);
                    b1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                    layouthandler.imgView.setImageBitmap(b1);
                    b1 = null;
                }
                Log.e("fouth step", "int that is" );
                if ((video==1)&&(preview!=null))
                {
                    layouthandler.play.setVisibility(View.VISIBLE);
                    Log.e("loggyouyt", "int that is" + video);
                    layouthandler.playvideo.setVisibility(View.VISIBLE);
                   Bitmap bitmap = BitmapFactory.decodeByteArray(preview, 0, preview.length);
                    layouthandler.playvideo.setImageBitmap(bitmap);
                    b1 = null;



                }
                Log.e("fivth step", "int that is" );
                Log.e("content","is"+alarm1+"video"+video+"img"+img);

          } catch (Exception e) {

            }
        }
        catch (OutOfMemoryError o)
        {

        }
        return row;
    }


    private void readBitmapInfo(Bitmap bm) {

        try {

            try {


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                float BYTES_PER_PX = 4.0f; //32 bit

                float imageHeight = bm.getHeight();
                float imageWidth = bm.getWidth();
                String imageMimeType = options.outMimeType;
                float estimatedimagesize = imageWidth * imageHeight * BYTES_PER_PX / BYTES_IN_MB;
                //Log.e("dlfkf", "w,h, type:" + imageWidth + ", " + imageHeight + ", " + imageMimeType);
                Log.e("image", "estimated memory required in MB:  " + imageWidth * imageHeight * BYTES_PER_PX / BYTES_IN_MB);

            } catch (Exception e) {
            }
        }
        catch (OutOfMemoryError q)
        {

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


}




