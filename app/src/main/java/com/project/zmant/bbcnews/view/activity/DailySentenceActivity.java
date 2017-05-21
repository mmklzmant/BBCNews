package com.project.zmant.bbcnews.view.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.DailyCardViewBean;
import com.project.zmant.bbcnews.component.DaggerDailyComponent;
import com.project.zmant.bbcnews.module.DailyModule;
import com.project.zmant.bbcnews.presenter.DailyPresenter;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.view.DApplication;
import com.project.zmant.bbcnews.view.adapter.DailyCardViewAdapter;
import com.project.zmant.bbcnews.view.iviews.IDailyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 20:23
 * @classname DailySentenceActivity
 * @description 每日一句英语模块
 */

public class DailySentenceActivity extends BaseActivity implements IDailyView {
    @BindView(R.id.drawlayout_dailysentence)
    DrawerLayout mDrawerLayout;
    ACache mACache;

    @Override
    protected DrawerLayout getDrawer() {
        return mDrawerLayout;
    }
    @BindView(R.id.floatbtn_daily)
    FloatingActionButton mFloatBtn;
    @BindView(R.id.toolbar_dailysentence)
    Toolbar mToolbar;
    @BindView(R.id.nav_dailysentence)
    NavigationView mNav;
    @BindView(R.id.recycleview_dailysentence)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_dailysentence)
    SwipeRefreshLayout mRefresh;

    DailyCardViewAdapter mCardViewAdapter;
    ArrayList<DailyCardViewBean> mDatas;
    int position = 0; //显示的item下标\
    boolean isLoad = false;
    boolean isPull = true;

    ActionBarDrawerToggle mToggle;
    @Inject
    ApiService apiService;
    @Inject
    DailyPresenter mPresenter;

    @Override
    void initViews() {
        setContentView(R.layout.dailysentence_layout);
        mACache = ACache.get(this);
    }

    @Override
    void initData() {
        mRefresh.setColorSchemeResources( R.color.colorPrimary);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mToggle);
        mPresenter.loadData(apiService, "", this);
    }

    @Override
    protected void setupComponent() {
        DaggerDailyComponent.builder()
                .appDailyComponent(DApplication.get(this).getAppDailyComponent())
                .dailyModule(new DailyModule(this))
                .build()
                .inject(this);
    }

    @Override
    void initListeners() {
        initFrontRefresh();
        initLaterRefresh();
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailySentenceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFrontRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                isLoad = false;
                mCardViewAdapter.AddHeaderItem(0);
                position = 0;
                mRefresh.setRefreshing(false);
            }
        });
    }

    private void initLaterRefresh() {
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == mCardViewAdapter.getItemCount()) {

                    if (isLoad || position == 0) {
                        mCardViewAdapter.changeMoreStatus(mCardViewAdapter.LOADING_MORE);
                        if (position == mDatas.size() - 1) {
                            position = -1;
                        } else {
                            mCardViewAdapter.AddFooterItem(++position);
                        }
                        mRecycleView.scrollToPosition(0);
                    }
                    isLoad = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolBarTitle() {
        return "Daily Sentence";
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
    public void showData(ArrayList<DailyCardViewBean> datas, int flag) {
        mDatas = datas;
        mCardViewAdapter = new DailyCardViewAdapter(this, datas);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mCardViewAdapter);
        if(flag == 0)
        {
            storeData();
        }
    }

    private void storeData()
    {
        DailyCardViewBean bean = null;
        JSONArray array = new JSONArray();
        JSONObject obj = null;
        for (int i = 0; i < mDatas.size(); i++)
        {
            bean = mDatas.get(i);
            obj = new JSONObject();
            try {
                obj.put("auther", bean.getAuthor());
                obj.put("chText", bean.getChText());
                obj.put("descrip", bean.getDescip());
                obj.put("enText", bean.getEnText());
                obj.put("header", bean.getHeader());
                obj.put("imgurl", bean.getImgurl());
                array.put(i, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mACache.put("daily", array);
    }

    @Override
    public void showProgress(boolean isShow) {
        mRefresh.setRefreshing(isShow);
    }
}
