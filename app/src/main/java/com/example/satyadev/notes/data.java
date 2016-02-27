package com.example.satyadev.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by satyadev on 26-08-2015.
 */
public class data  extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;
    private static final String databasename  = "db1";
     static String tablename = "db2";
    private static final String time1 = "time";
    private static final String column2 = "title";
    private static final String id = "id";
    private static final String KEY_ID = "db1";
    private  static final String tablename2 = "notification";
    private  static final String tablename3 = "tablename3";
    private static final String tablename4 = "tablename4";
    private static  final String table4id = "id";
    private static final String table4noteid = "noteid";
    private static final String table3date = "table3date";
    private static final String table3time = "table3time";
    private static final String table4date = "notificationdate";
    private static final String table4time = "notificationtime";
    private static final String id2 = "id2";
    private static final String id3 = "id3";
    private static final String notification = "notification";
    private static final String notificationid = "notificationid";
    private static final String number = "number";
    private static final String number1 = "number1";
    private static final String column1 = "notes";
    private static final String date = "date";
    private static final String alarmtime = "alarmtime";
    private static final String tablename5 = "alarmtable";
    private static final String alarmid = "alarmid";
    private static final String alarmnumber="alarmnumber";
    private static final String cancel = "cancel";
    private static final String alarmstarttime="alarmstarttime";
    private static final String year = "year";
    private static final String month = "month";
    private static final String day = "day";
    private static final String hour = "hour";
    private static final String minute = "minute";
    private static  final String image = "image";
    private static final String test = "test";
    private static final String imagepath = "imagepath";
    private static final String intent = "intent";
    private static final String video = "video";
    private static final String preview = "preview";

    private static final String create1 = "CREATE TABLE "+ tablename+ "("+ id+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+ column1+" TEXT,"
      + time1+" TEXT,"  + video+" TEXT," + cancel+" TEXT," + imagepath+" TEXT,"  + intent+" TEXT," + preview+" BLOB," + image+" BLOB,"  + date+" TEXT,"  + column2+ " TEXT);";

    private static final String create2 = "CREATE TABLE "+ tablename2+ "("+ id2+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+ notification+" TEXT,"
             + number+ " TEXT);";

    private static final String create7 = "CREATE TABLE "+ tablename5+ "("+ id2+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+ alarmid+" TEXT,"
            + alarmnumber+ " TEXT);";


    private static final String create6 = "CREATE TABLE "+ tablename3+ "("+ id2+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+ notificationid+" TEXT,"
            + year+" TEXT,"  + month+" TEXT,"    + day+" TEXT,"  + hour+" TEXT,"  + minute+" TEXT,"
            + cancel+" TEXT,"    + alarmstarttime+" TEXT,"  + alarmtime+" TEXT,"
            + table3date+" TEXT,"  + table3time+ " TEXT);"    + number1+ " TEXT);";

    private static final String create5 = "CREATE TABLE "+ tablename4+ "("+ table4id+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+ table4noteid+" TEXT,"
            + cancel+" TEXT,"   + alarmstarttime+" TEXT,"   + table4date+" TEXT,"  + table4time+ " TEXT);";


    public void delete1(String id,SQLiteDatabase db) {
        db.execSQL("delete from " + tablename + " where id='" + id + "'");
        Log.e("work done", "compeke");
    }

    public void insert1(String title,  SQLiteDatabase db)
    {

        ContentValues cv = new ContentValues();
        cv.put(notification,title);
        db.insert(tablename2, null, cv);

    }

    public void table2(String title, String note ,SQLiteDatabase db)
    {

        ContentValues cv = new ContentValues();
        cv.put(test, title);
        cv.put(notification,note);
        db.insert(tablename2, null, cv);
      //  Log.e("enter","in table 2"+title);

    }




    public void insertalarm(String title,  SQLiteDatabase db)
    {

        ContentValues cv = new ContentValues();
        cv.put(alarmid,title);
        db.insert(tablename5, null, cv);

    }

    public String getTablename()
{
    return tablename;
}

    public String getTablename2()
    {
        return tablename2;
    }


    private static final int dbversion = 53;


    public data(Context context){

        super(context, databasename, null, dbversion);



    }


/**  This method is called first time*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(create1);
        db.execSQL(create2);
        db.execSQL(create5);
        db.execSQL(create6);
        db.execSQL(create7);
        insertalarm("0", db);
        insert1("0", db);

    }


    public void insertvideo(SQLiteDatabase db,String videocount, String intent1,byte[] preview1,String timedate,String title
    ,String note)
    {

        ContentValues cv = new ContentValues();
        cv.put(intent, intent1);
        cv.put(date, timedate);
        cv.put(preview, preview1);
        cv.put(column1, title);
        cv.put(column2, note);
        Log.e("intent", "value" + intent1);
        cv.put(video, videocount);
        db.insert(tablename, null, cv);


    }

    public Cursor fetchvideo(SQLiteDatabase db,String identity)
    {

        Cursor cursor;
        String  con = id+" Like ?";
        String [] condition = {identity};
        String [] projection = { intent,video,column1,column2,preview};
        cursor = db.query(tablename,projection,con,condition,null,null,null);
        Log.e("fetch", "video successfull" + cursor + identity);
        return cursor;
    }

    public Cursor fetchplayvideo(SQLiteDatabase db,String identity)
    {

        Cursor cursor;
        String  con = id+" Like ?";
        String [] condition = {identity};
        String [] projection = { intent,video};
        cursor = db.query(tablename,projection,con,condition,null,null,null);
        Log.e("fetch", "video successfull" + cursor + identity);
        return cursor;
    }

    public int upadatevideo(SQLiteDatabase sql,String intent1,String pass,byte[] previewimage,String timedate,String title
    ,String note,String videosign)
    {
        ContentValues cv = new ContentValues();
        cv.put(date,timedate);
        cv.put(intent,intent1);
        cv.put(column1, title);
        cv.put(column2, note);
        cv.put(preview,previewimage);
        cv.put(video,videosign);
        String con = id+ " Like ?";
        String [] condition = {pass};
        Log.e("updare","videoo"+intent1+pass+timedate+title+note+previewimage);
        int numberrow =  sql.update(tablename,cv,con,condition);

        return numberrow;

    }


    public void insert(String title, String note,  SQLiteDatabase db,String timedata,
                       String time,byte[] image1,String imagepath1,String intent1)
    {


        ContentValues cv = new ContentValues();

            cv.put(image,image1);
            cv.put(imagepath,imagepath1);
            cv.put(intent, intent1);
            Log.e("intent", "value" + intent1);


        cv.put(column1, title);
        cv.put(column2, note);
        cv.put(time1, timedata);
        cv.put(date, time);
        db.insert(tablename, null, cv);


    }


    public void setnotificationdata(String id ,SQLiteDatabase db,String date1,String time,long alarm,int cal,String datetime,String alarmid1,String year1,String  month1,String day1,String hour1,String minute1)
    {

        String res=Long.toString(alarm);
        ContentValues cv = new ContentValues();
        cv.put(notificationid,id);
        cv.put(table3date,date1);
        cv.put(table3time,time);
        cv.put(alarmtime,res);
        cv.put(cancel,alarmid1);
        cv.put(alarmstarttime,datetime);
        cv.put(year, year1);
        cv.put(month,month1);
        cv.put(day,day1);
        cv.put(hour,hour1);
        cv.put(minute,minute1);
        db.insert(tablename3, null, cv);

    }


    public void setnotificationdataalarm(String id ,SQLiteDatabase db,String date1,String time,String datetime,String alarmid1)
    {

        ContentValues cv = new ContentValues();
        cv.put(table4noteid,id);
        cv.put(table4date,date1);

        cv.put(table4time, time);
        cv.put(alarmstarttime,datetime);
        cv.put(cancel,alarmid1);
        db.insert(tablename4, null, cv);


    }

    public Cursor searchresult(SQLiteDatabase db,String identity)
    {


        Cursor c;
        String  order = id + " DESC" ;
        String  con = column1+" Like ?";
        String [] condition = {"%" + identity + "%"};
        String [] projection = { column1,column2,id,image,video,preview};
        c = db.query(tablename,projection,con,condition,null,null,order);
Log.e("entery","is the"+identity);
        return c;
    }


    public Cursor getdetaildata1(String tbname,SQLiteDatabase db,String identity)
    {

        Cursor cursor;
        String  con = id+" Like ?";
        String [] condition = {identity};
        String [] projection = { column1,column2,id,time1,cancel,image,imagepath,intent};
        cursor = db.query(tbname,projection,con,condition,null,null,null);
        return cursor;
    }



    public Cursor getdetaildataalarm(SQLiteDatabase db,String identity)
    {
       // Log.e("identity","check"+identity);
        Cursor cursor;
        String  con = notificationid+" Like ?";
        String [] condition = {identity};
        String [] projection = { notificationid,cancel,year,month,day,hour,minute};
        cursor = db.query(tablename3,projection,con,condition,null,null,null);

        return cursor;
    }

    public Cursor checkgetdetaildataalarm(SQLiteDatabase db)
    {

        Cursor cursor;
        //String  con = notificationid+" Like ?";
        //String [] condition = {identity};
        String [] projection = { notificationid,cancel,year,month,day,hour,minute};
        cursor = db.query(tablename3,projection,null,null,null,null,null);

        return cursor;
    }



    public Cursor getalarmnumber(SQLiteDatabase db)
    {

        Cursor cursor;
        String [] projection = { alarmid};
        cursor = db.query(tablename5,projection,null,null,null,null,null);

        return cursor;
    }


    public int setalarmnumber(SQLiteDatabase db,String count,String newcount)
    {

        ContentValues cv = new ContentValues();
        cv.put(alarmid,newcount);
        String con = alarmid+ " Like ?";
        String [] condition = {count};
        int numberrow =  db.update(tablename5,cv,con,condition);

        return numberrow;
    }





    public Cursor getnotificationdetaildata1(SQLiteDatabase db,String identity)
    {

        Cursor cursor;
        String  con = id+" Like ?";
        String [] condition = {identity};
        String [] projection = { column1,column2,id,time1,image,video,preview};
        cursor = db.query(tablename,projection,con,condition,null,null,null);

        return cursor;
    }


    public int upadateuri(String pass,String intent1,SQLiteDatabase sql)
    {
        ContentValues cv = new ContentValues();

        cv.put(intent,intent1);

        String con = id+ " Like ?";
        String [] condition = {pass};
        int numberrow =  sql.update(tablename,cv,con,condition);
        Log.e("uri","work is okk");

        return numberrow;

    }



    public int upadate(String newname,String newnote,String oldname,SQLiteDatabase sql,String time,byte[] image1,String imagepath1, String pass,String intent1)
    {
        ContentValues cv = new ContentValues();
       // if(image1!=null)
        //{
            cv.put(image,image1);
            cv.put(imagepath,imagepath1);
        cv.put(intent,intent1);
        //}
        cv.put(column1,newname);
        cv.put(column2, newnote);
        cv.put(date, time);
        String con = id+ " Like ?";
        String [] condition = {pass};
        int numberrow =  sql.update(tablename,cv,con,condition);

        return numberrow;

    }




    public int upadatenotificationumber(SQLiteDatabase sql,String notificationid,String count,String cancel1)
    {
        ContentValues cv = new ContentValues();
        cv.put(time1,count);
        cv.put(cancel,cancel1);
        String con = id+ " Like ?";
        String [] condition = { notificationid};
        int numberrow =  sql.update(tablename,cv,con,condition);

        return numberrow;

    }


    public int upadatenotificationumberdelete(SQLiteDatabase sql,String notificationid,String count)
    {
        ContentValues cv = new ContentValues();
        cv.put(time1,count);
        String con = id+ " Like ?";
        String [] condition = { notificationid};
        int numberrow =  sql.update(tablename,cv,con,condition);

        return numberrow;

    }




    public void delete(SQLiteDatabase sql,String identity)
    {
        String con = id+ " Like ?";
        String[] condtition = {identity};
        sql.delete(tablename, con, condtition);
    }





    public void deletenotificationalarm(SQLiteDatabase sql,String identity)
    {
        String con = cancel+ " Like ?";
        String[] condtition = { identity};
        sql.delete(tablename3, con, condtition);

    }




    public void deletenotificationentry(SQLiteDatabase sql,String identity)
    {
        String con = cancel+ " Like ?";
        String[] condtition = {identity};
        sql.delete(tablename4, con, condtition);
    }



    public Cursor getdata(SQLiteDatabase db)
    {

        Cursor cursor;
        String  order = id + " DESC" ;
        String [] projection = { column1,column2,id,time1,date,cancel,image,imagepath};
        cursor = db.query(tablename,projection,null,null,null,null,order);

        return cursor;
    }

    public Cursor getdatalimit(SQLiteDatabase db)
    {

        Cursor cursor;
        String  order = id + " DESC" ;
        String [] projection = { column1,column2,id,time1,date,cancel,image,imagepath,intent,video,preview};
        cursor = db.query(tablename,projection,null,null,null,null,order);

        return cursor;
    }

    public Cursor getnotificationdata(SQLiteDatabase db,long alarm)

    {
        String res=Long.toString(alarm);
        Cursor cursor;
        String con = alarmtime+ " Like ?";
        String[] condition = { res};
        String [] projection = { notificationid,table3time,table3date,alarmstarttime,cancel,alarmtime};
        cursor = db.query(tablename3,projection,con,condition,null,null,null);


        return cursor;
    }

    public Cursor getnotificationdataalarm(SQLiteDatabase db)
    {


        Cursor cursor;
        String [] projection = { table4noteid,table4time,table4date,alarmstarttime,cancel};
        cursor = db.query(tablename4,projection,null,null,null,null,null);

        return cursor;
    }

    public Cursor getnotificationdataalarmtable3(SQLiteDatabase db)
    {


        Cursor cursor;
        String [] projection = { notificationid,table3time,table3date,alarmstarttime,alarmtime,cancel,year,month,day,hour,minute};
        cursor = db.query(tablename3,projection,null,null,null,null,null);

        return cursor;
    }



    public Cursor getdataalarm(SQLiteDatabase db)
    {


        Cursor cursor;
        String [] projection = { notificationid,table3time,table3date,alarmtime,cancel,alarmstarttime};
        cursor = db.query(tablename3,projection,null,null,null,null,null);
        return cursor;
    }



/** This method is used to update database by create new table , addc column, delete column */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // String upgradeQuery = "ALTER TABLE db2 ADD COLUMN date TEXT";
      // String upgradeQuery = "ALTER TABLE  tablename3 ADD COLUMN alarmtime TEXT";
       // db.execSQL(create5);
     //   db.execSQL(create7);


     //   insertalarm("0", db);

        String upgradeQuery2 = "ALTER TABLE db2 ADD COLUMN video  TEXT";
        db.execSQL(upgradeQuery2);

        String upgradeQuery1 = "ALTER TABLE db2 ADD COLUMN preview BLOB";
        db.execSQL(upgradeQuery1);

      //  String upgradeQuery1 = "ALTER TABLE db2 ADD COLUMN image BLOB";
       // db.execSQL(upgradeQuery1);


        //String upgradeQuery1 = "ALTER TABLE tablename3 ADD COLUMN alarmstarttime TEXT";
       // db.execSQL(upgradeQuery1);

//
     //   String upgradeQuery2 = "ALTER TABLE tablename4 ADD COLUMN cancel TEXT";
     //   db.execSQL(upgradeQuery2);




        // Log.e("upgrade done","completefdtwd");
      // db.execSQL("DROP TABLE IF EXISTS " + tablename);
      //  db.execSQL("DROP TABLE IF EXISTS " + tablename4);
     //  db.execSQL(create1);
        Log.e("upgrade done","completefdtwd");

    }
}

