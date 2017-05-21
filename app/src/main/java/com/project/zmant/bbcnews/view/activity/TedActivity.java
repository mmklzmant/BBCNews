package com.project.zmant.bbcnews.view.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.TedTabBean;
import com.project.zmant.bbcnews.view.adapter.TedViewPagerAdapter;
import com.project.zmant.bbcnews.view.fragment.FragmentNewest;
import com.project.zmant.bbcnews.view.fragment.FragmentTopTed;
import com.project.zmant.bbcnews.view.fragment.FragmentTrending;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 19:56
 * @classname TedActivity
 * @description Ted 模块
 */

public class TedActivity extends BaseActivity {
    @BindView(R.id.toolbar_ted)
    Toolbar mToolbar;
    @BindView(R.id.drawlayout_ted)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_ted)
    NavigationView mNav;
    @BindView(R.id.floatbtn_ted)
    FloatingActionButton mFloatBtn;

    @BindView(R.id.viewpater_ted)
    ViewPager mViewPager;
    @BindView(R.id.tablayout_ted)
    TabLayout mTabLayout;
    ArrayList<TedTabBean> mTabDatas;
    TedViewPagerAdapter mViewPageAdapter;

    ActionBarDrawerToggle mToggle;

    @Override
    void initViews() {
        setContentView(R.layout.ted_layout);
    }

    @Override
    void initData() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mToggle);
        initTabLayout();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        initTabBeans();
        mViewPageAdapter = new TedViewPagerAdapter(getSupportFragmentManager(),this, mTabDatas);
        mViewPager.setAdapter(mViewPageAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 初始化TabBeans
     */
    private void initTabBeans() {
        mTabDatas = new ArrayList<>();
        mTabDatas.add(new TedTabBean("Newest", new FragmentNewest(this, R.layout.fragment_newest_ted)));
        mTabDatas.add(new TedTabBean("Trending", new FragmentTrending(this, R.layout.fragment_trending_ted)));
        mTabDatas.add(new TedTabBean("Top", new FragmentTopTed(this, R.layout.fragment_topted_ted)));
    }

    @Override
    void initListeners() {
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setupComponent() {

    }

    @Override
    Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected ActionBarDrawerToggle getDrawerToggle() {
        return mToggle;
    }

    @Override
    protected NavigationView getNav() {
        return mNav;
    }

    @Override
    protected DrawerLayout getDrawer() {
        return mDrawerLayout;
    }

    @Override
    protected BaseActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected String getToolBarTitle() {
        return "TED";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
    }
}
