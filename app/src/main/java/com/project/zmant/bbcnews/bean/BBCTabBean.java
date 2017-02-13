package com.project.zmant.bbcnews.bean;

import android.support.v4.app.Fragment;

import com.project.zmant.bbcnews.view.fragment.BaseMainFragment;

/**
 * @author zmant 2016/11/29 14:57
 * @classname BBCTabBean
 * @description 封装Tab Titles和Fragment的布局Id
 */

public class BBCTabBean {
    private String titles;
    private Fragment fragment;

    public BBCTabBean(String title, Fragment fragment)
    {
        this.titles = title;
        this.fragment = fragment;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseMainFragment fragment) {
        this.fragment = fragment;
    }
}
