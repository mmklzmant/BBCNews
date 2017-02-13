package com.project.zmant.bbcnews.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zmant.bbcnews.bean.TedTabBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/11/29 13:57
 * @classname MainViewPagerAdapter
 * @description ViewPager的适配器
 */

public class TedViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<TedTabBean> mTabBean;
    private Context context;

    public TedViewPagerAdapter(FragmentManager fm, Context context, ArrayList<TedTabBean> tabBeans)
    {
        super(fm);
        this.context = context;
        this.mTabBean = tabBeans;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabBean.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mTabBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabBean.get(position).getTitles();
    }
}
