package com.example.satyadev.notes;

/**
 * Created by satyadev on 28-08-2015.
 */


/** In this class, is used to separate item of listview's items means this class differentiate date of every
    item of the listview of Mainactivity  */

/** set methos are used to set the values and get method are used to get the values for example
    when user click on the item of listview then get the data of that item is using  get methods*/

public class dataprovider {

    private String name;
    private String cancel;

    public String getIntt() {
        return intt;
    }

    public void setIntt(String intt) {
        this.intt = intt;
    }

    private  byte[] img = null;

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    private byte[] preview;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String pass,intt;


    private int  id,video;
    private String time,alarm;
    private String date;

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public dataprovider(String name, String pass,int id,String time,String date,String alarm,String cancel,byte[] img,int video,String intt,byte[] preview)
    {
        this.preview = preview;
        this.intt = intt;
        this.video = video;
        this.img = img;
        this.id = id;
        this.cancel  = cancel;
        this.alarm = alarm;
        this.name  = name;
        this.pass =  pass;
        this.time = time;
        this.date = date;



    }
}
