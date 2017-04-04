package com.project.zmant.bbcnews.view.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.bean.BBCTabBean;
import com.project.zmant.bbcnews.view.adapter.MainViewPagerAdapter;
import com.project.zmant.bbcnews.view.fragment.FragmentAsia;
import com.project.zmant.bbcnews.view.fragment.FragmentBusiness;
import com.project.zmant.bbcnews.view.fragment.FragmentChina;
import com.project.zmant.bbcnews.view.fragment.FragmentMagazine;
import com.project.zmant.bbcnews.view.fragment.FragmentScience;
import com.project.zmant.bbcnews.view.fragment.FragmentWorld;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.drawlayout_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.nav_main)
    NavigationView mNav;

    @BindView(R.id.viewpager_main)
    ViewPager mViewPager;
    @BindView(R.id.tablayout_main)
    TabLayout mTabLayout;
    MainViewPagerAdapter mMainViewPagerAdapter;
    ArrayList<BBCTabBean> mBBCTabBeen;

    FragmentWorld fragmentWorld;
    FragmentAsia fragmentAsia;
    FragmentScience fragmentScience;
    FragmentMagazine fragmentMagazine;
    FragmentBusiness fragmentBusiness;
    FragmentChina fragmentChina;

    ActionBarDrawerToggle mToggle;
    ArrayList<BBCCardViewBean> mDatas;//test
    int[] layoutIds = {R.layout.fragment_world_main, R.layout.fragment_asia_main,
                    R.layout.fragment_business_main, R.layout.fragment_science_main,
                    R.layout.fragment_magazine_main, R.layout.fragment_china_main};

    @Override
    protected DrawerLayout getDrawer() {
        return mDrawerLayout;
    }

    @Override
    void initViews() {
        setContentView(R.layout.bbcnews_main);
    }

    @Override
    void initData() {

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mToggle);
        mViewPager.setOffscreenPageLimit(5);
        initFragment();
        initTabLayout();
        setFloatBtnListener();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragmentWorld = new FragmentWorld(this, layoutIds[0]);
        fragmentAsia = new FragmentAsia(this, layoutIds[1]);
        fragmentBusiness = new FragmentBusiness(this, layoutIds[2]);
        fragmentScience = new FragmentScience(this, layoutIds[3]);
        fragmentMagazine = new FragmentMagazine(this, layoutIds[4]);
        fragmentChina = new FragmentChina(this, layoutIds[5]);
    }

    /**
     * 设置floatbutton监听器
     */
    private void setFloatBtnListener() {
    }



    @Override
    protected void setupComponent() {

    }


    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        initTabBeans();
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), this, mBBCTabBeen);
        mViewPager.setAdapter(mMainViewPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    /**
     * 初始化tablayout所需数据
     */
    public void initTabBeans() {
        mBBCTabBeen = new ArrayList<>();
        if(fragmentWorld == null)
        {
            Log.i("null", "fragmentworld is null");
        }
        mBBCTabBeen.add(new BBCTabBean("World", fragmentWorld));
        mBBCTabBeen.add(new BBCTabBean("Asia", fragmentAsia));
        mBBCTabBeen.add(new BBCTabBean("China", fragmentChina));
        mBBCTabBeen.add(new BBCTabBean("Business", fragmentBusiness));
        mBBCTabBeen.add(new BBCTabBean("Science", fragmentScience));
        mBBCTabBeen.add(new BBCTabBean("Magazine", fragmentMagazine));
    }

    @Override
    void initListeners() {

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
    protected BaseActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected String getToolBarTitle() {
        return "BBCNews";
    }
}
