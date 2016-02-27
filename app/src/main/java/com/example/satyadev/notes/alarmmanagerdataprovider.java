package com.example.satyadev.notes;

/**
 * Created by satyadev on 16-09-2015.
 */


/** In this class, is used to separate item of listview's items means this class differentiate date of every
 item of the listview of alarmanager  */

/** set methos are used to set the values and get method are used to get the values for example
 when user click on the item of listview then get the data of that item is using  get methods*/

public class alarmmanagerdataprovider {

    private String title;
    private String message,date,time;
    private String id;

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    private byte[] image = null;
    private byte[] preview = null;
    private int video;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String datetime;

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    private String cancel;

    public String getAlarmtime() {
        return alarmtime;
    }

    public void setAlarmtime(String alarmtime) {
        this.alarmtime = alarmtime;
    }

    private String alarmtime;

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    private String time1;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public alarmmanagerdataprovider(String title,String message,String id,String time,String date,String time1,String alarmtime,String cancel,String datetime,byte[] image,int video,byte[] preview)

    {
        this.video = video;
        this.preview = preview;
        this.image = image;
        this.datetime = datetime;
        this.cancel  = cancel;
        this.time1 = time1;
        this.alarmtime = alarmtime;
        this.date = date;
        this.time = time;
        this.title = title;
        this.message = message;
        this.id = id;
    }
}
