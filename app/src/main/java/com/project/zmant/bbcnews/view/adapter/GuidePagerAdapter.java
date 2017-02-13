package com.project.zmant.bbcnews.view.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author zmant 2016/11/28 16:29
 * @classname GuidePageAdapter
 * @description 引导页ViewPager的PagerAdapter
 */

public class GuidePagerAdapter extends PagerAdapter {
    private   ArrayList<View> mViews;

    public GuidePagerAdapter(ArrayList<View> views) {
        this.mViews = views;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
       container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
