package com.project.zmant.bbcnews.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zmant.bbcnews.bean.BBCTabBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/11/29 13:57
 * @classname MainViewPagerAdapter
 * @description ViewPager的适配器
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BBCTabBean> mBBCTabBean;
    private Context context;

    public MainViewPagerAdapter(FragmentManager fm, Context context, ArrayList<BBCTabBean> BBCTabBeen)
    {
        super(fm);
        this.context = context;
        this.mBBCTabBean = BBCTabBeen;
    }

    @Override
    public Fragment getItem(int position) {
        return mBBCTabBean.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mBBCTabBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBBCTabBean.get(position).getTitles();
    }
}
