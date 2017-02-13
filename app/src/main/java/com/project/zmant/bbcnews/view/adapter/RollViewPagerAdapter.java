package com.project.zmant.bbcnews.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;

/**
 * @author zmant 2016/11/30 10:24
 * @classname RollViewPagerAdapter
 * @description RollPageView的适配器
 */

public class RollViewPagerAdapter extends StaticPagerAdapter {
    ArrayList<View> mViews;

    public RollViewPagerAdapter(ArrayList<View> views)
    {
        this.mViews = views;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }
}
