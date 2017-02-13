package com.project.zmant.bbcnews.bean;

/**
 * @author zmant 2016/11/30 16:19
 * @classname BBCCardViewBean
 * @description BBCNews CardView所需数据的模板
 */

public class BBCCardViewBean {
    private String imagUrl;
    private String title;
    private String time;
    private String location;
    private String url;

    public BBCCardViewBean()
    {

    }
    public BBCCardViewBean(String title, String url, String img,
                           String time, String location)
    {
        this.imagUrl = img;
        this.time = time;
        this.title = title;
        this.location = location;
        this.url = url;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
