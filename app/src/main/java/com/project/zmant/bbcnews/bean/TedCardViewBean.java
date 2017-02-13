package com.project.zmant.bbcnews.bean;

/**
 * @author zmant 2016/11/30 17:53
 * @classname
 * @description
 */

public class TedCardViewBean {
    private String imgurl;
    private String title;
    private String url;
    private String speaker;
    private String description;

    public TedCardViewBean()
    {

    }
    public TedCardViewBean(String imgurl, String title, String url)
    {
        this.title = title;
        this.imgurl = imgurl;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
