package com.example.satyadev.notes;

/**
 * Created by satyadev on 18-12-2015.
 */
public class searchdataprovider {
    private String title;
    private  int video;

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    private String message,date,time,datetime;
private String id,cancel;
    private byte[] image = null;
    private byte[] preview = null;

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
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
        public searchdataprovider(String title,String message,String id,byte[] image,int video,byte[] preview)

        {
            this.video = video;
            this.preview = preview;
            this.image = image;
            this.title = title;
            this.message = message;
            this.id = id;
        }
}
