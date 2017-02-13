package com.project.zmant.bbcnews.view.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.DaggerTedDetailComponent;
import com.project.zmant.bbcnews.module.TedDetailModule;
import com.project.zmant.bbcnews.presenter.TedDetailPresenter;
import com.project.zmant.bbcnews.view.DApplication;
import com.project.zmant.bbcnews.view.iviews.ITedDetailView;

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/12/27 20:33
 * @classname TedDetailActivity
 * @description Ted 模块 Cardview点击跳转的Activity
 */

public class TedDetailActivity extends BaseActivity implements ITedDetailView {
    @BindView(R.id.drawlayout_ted_detail)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.refresh_ted_detail)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.tv_title_ted_detail)
    TextView mTitleTV;
    @BindView(R.id.ll_ted_detail)
    LinearLayout mLinear;
    @BindView(R.id.toolbar_ted_detail)
    Toolbar mToolBar;
    @BindView(R.id.nav_ted_detail)
    NavigationView mNav;
    @Inject
    ApiService apiService;
    @Inject
    TedDetailPresenter mPresenter;
    ActionBarDrawerToggle mToggle;
    Intent intent;
    String title;
    String url;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return true;
    }

    @Override
    void initViews() {
        setContentView(R.layout.ted_detail);
    }

    @Override
    void initData() {
        mRefresh.setColorSchemeResources( R.color.colorPrimary);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mToggle);
        intent = this.getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        mPresenter.loadData(apiService,
                 url + "/transcript?language=en");
    }

    @Override
    void initListeners() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData(apiService,
                        url + "/transcript?language=en");
            }
        });
    }

    @Override
    Toolbar getToolbar() {
        return mToolBar;
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
        return "Ted";
    }

    @Override
    protected void setupComponent() {
        DaggerTedDetailComponent.builder()
                .appTedComponent(DApplication.get(this).getAppTedComponent())
                .tedDetailModule(new TedDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showData(Map<String, String> map) {
        mTitleTV.setText(title);
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext())
        {
            String key = iter.next();
            String value = map.get(key);
            TextView tvTime = new TextView(this);
            tvTime.setText(key);
            mLinear.addView(tvTime);
            TextView tvContent = new TextView(this);
            tvContent.setText(value);
            mLinear.addView(tvContent);
        }
    }

    @Override
    public void showFailure(String failure) {
        TextView tv = new TextView(this);
        tv.setText(failure);
        mLinear.addView(tv);
    }

    @Override
    public void showProgress() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hidProgress() {
        mRefresh.setRefreshing(false);
    }
}
