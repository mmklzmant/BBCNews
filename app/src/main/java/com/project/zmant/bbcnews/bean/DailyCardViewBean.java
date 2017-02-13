package com.project.zmant.bbcnews.bean;

/**
 * @author zmant 2016/12/27 16:43
 * @classname
 * @description Daily模块的Cardview 所需的数据封装
 */

public class DailyCardViewBean {
    private String header;
    private String enText;
    private String author;
    private String chText;
    private String descip;
    private String imgurl;

    public DailyCardViewBean()
    {

    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getEnText() {
        return enText;
    }

    public void setEnText(String enText) {
        this.enText = enText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChText() {
        return chText;
    }

    public void setChText(String chText) {
        this.chText = chText;
    }

    public String getDescip() {
        return descip;
    }

    public void setDescip(String descip) {
        this.descip = descip;
    }
}
