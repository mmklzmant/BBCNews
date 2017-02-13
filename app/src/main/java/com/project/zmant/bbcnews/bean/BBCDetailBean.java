package com.project.zmant.bbcnews.bean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/1 11:15
 * @classname BBCDetailBean
 * @description BBCnews详情
 */

public class BBCDetailBean {
    private  String title;
    private ArrayList<String> imgurls;
    private ArrayList<String> paragraphs;

    public BBCDetailBean()
    {

    }
    public BBCDetailBean(String title, ArrayList<String> imgurls,
                          ArrayList<String> paragraphs)
    {
        this.title = title;
        this.imgurls = imgurls;
        this.paragraphs = paragraphs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getImgurls() {
        return imgurls;
    }

    public void setImgurls(ArrayList<String> imgurls) {
        this.imgurls = imgurls;
    }

    public ArrayList<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(ArrayList<String> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
