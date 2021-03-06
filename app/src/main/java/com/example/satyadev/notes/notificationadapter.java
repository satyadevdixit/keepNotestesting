package com.example.satyadev.notes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.satay.dev.example.notes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyadev on 05-09-2015.
 */
public class notificationadapter extends ArrayAdapter {
    List list = new ArrayList();
    View view;
    private byte[] img = null;
    public static final float BYTES_IN_MB = 1024.0f * 1024.0f;


    public notificationadapter(Context context, int resource) {
        super(context, resource);

    }


    static class layouthandler {
        TextView name, pass, date, time, datetime;
        ImageView imgView;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
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
        View row = convertView;
        layouthandler layouthandler;


        try {


            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.notificationshow, parent, false);
                layouthandler = new layouthandler();
                layouthandler.name = (TextView) row.findViewById(R.id.name);
                layouthandler.pass = (TextView) row.findViewById(R.id.pass);
                layouthandler.date = (TextView) row.findViewById(R.id.date);
                layouthandler.imgView = (ImageView) row.findViewById(R.id.imgView);
                row.setTag(layouthandler);

            } else {
                layouthandler = (layouthandler) row.getTag();

            }

            notificationdataprovider notificationdataprovider = (notificationdataprovider) this.getItem(position);
            layouthandler.imgView.setVisibility(View.GONE);
            layouthandler.name.setText((notificationdataprovider.getTitle()).toUpperCase());
            layouthandler.pass.setText(notificationdataprovider.getMessage());
            String date = notificationdataprovider.getDate();
            String time = notificationdataprovider.getTime();
            String datetime = time + "   " + date;
            layouthandler.date.setText(datetime);

            img = null;
            img = notificationdataprovider.getImage();
            if (img != null) {

                layouthandler.imgView.setVisibility(View.VISIBLE);
                clear();
                Bitmap b1 = BitmapFactory.decodeByteArray(img, 0, img.length);
                int imagesize = b1.getByteCount();
                float image = ((float) imagesize) / BYTES_IN_MB;


                float mbTotal = megabytesAvailable();
                final String totAvailable = "total MB available: " + mbTotal;
                Log.e("mem", "ory total" + totAvailable);
                float mbFree = megabytesFree();
                final String totFree = "free MB available: " + mbFree;
                Log.e("availabe", "in mb" + totFree);
                Log.e("imagelist", "size" + image);
//while(image>mbFree)
//{
              //  Log.e("bit", "map" + b1);
           //     Bitmap newbitmap = getResizedBitmap(b1);
             //   imagesize = newbitmap.getByteCount();
                image = ((float) imagesize) / BYTES_IN_MB;
                mbFree = megabytesFree();
                //  b1 = newbitmap;
               // Log.e("list", " mb" + totFree);
               // Log.e("listimage", "size" + image);

//}
                layouthandler.imgView.setImageBitmap(b1);
                // ImageView imageview = (ImageView) findViewById(R.id.imgView);
                // imageview.setImageBitmap(b1);

            }
        }
        catch (Exception e)
        {

        }

            return row;



    }


    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) width) / 2;
        float scaleHeight = ((float) height) / 2;
        // CREATE A MATRIX FOR THE MANIPULATION

        Log.e("value","of bitmap"+bm);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = bm.createScaledBitmap(bm, (int)(bm.getWidth()*0.5), (int)(bm.getHeight()*0.5), false);
        return resizedBitmap;
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


}