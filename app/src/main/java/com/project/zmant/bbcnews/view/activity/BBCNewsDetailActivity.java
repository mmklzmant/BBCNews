package com.project.zmant.bbcnews.view.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCDetailBean;
import com.project.zmant.bbcnews.component.DaggerBBCDetailComponent;
import com.project.zmant.bbcnews.module.BBCDetailModule;
import com.project.zmant.bbcnews.presenter.BBCDetailPresenter;
import com.project.zmant.bbcnews.utils.DensityUtil;
import com.project.zmant.bbcnews.view.DApplication;
import com.project.zmant.bbcnews.view.iviews.IBBCDetailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/30 9:37
 * @classname BBCNewsDetail
 * @description 展示bbc news的页面
 */

public class BBCNewsDetailActivity extends BaseActivity implements IBBCDetailView {
    @BindView(R.id.drawlayout_bbcnews_detail)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.refresh_bbcnews_detail)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.tv_title_bbcnews_detail)
    TextView mTitleTV;
    @BindView(R.id.tv_introduction_cardview_main)
    TextView mIntroTV;
    @BindView(R.id.tv_detail_bbcnews)
    TextView mDetailTV;
    @BindView(R.id.ll_bbcnews_detail)
    LinearLayout mLinear;
    @BindView(R.id.iv_one_bbcnews_detail)
    ImageView mOneImgView;

    @Inject
    ApiService apiService;
    @Inject
    BBCDetailPresenter mPresenter;

    Intent intent;
    String url;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return true;
    }

    @BindView(R.id.toolbar_bbcnews_detail)
    Toolbar mToolBar;
    @BindView(R.id.nav_bbcnews_detail)
    NavigationView mNav;

    ActionBarDrawerToggle mToggle;

    @Override
    void initViews() {
        setContentView(R.layout.content_detail);
    }

    @Override
    protected DrawerLayout getDrawer() {
        return mDrawerLayout;
    }

    @Override
    void initData() {
        mRefresh.setColorSchemeResources( R.color.colorPrimary);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mToggle);
        intent = this.getIntent();
        url = intent.getStringExtra("url");
        mPresenter.loadData(apiService, url);
    }

    @Override
    void initListeners() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData(apiService, url);
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
    protected BaseActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected String getToolBarTitle() {
        return "BBC News";
    }

    @Override
    protected void setupComponent() {
        DaggerBBCDetailComponent.builder()
                .appComponent(DApplication.get(this).getAppComponent())
                .bBCDetailModule(new BBCDetailModule(this))
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
    public void showData(BBCDetailBean bean) {
        mTitleTV.setText(bean.getTitle());
        ArrayList<String> detail = bean.getParagraphs();
        ArrayList<String> imgurls = bean.getImgurls();
        if(imgurls == null)
        {
            showVideo(detail);
        }
        else
        {
            showCommon(detail, imgurls);
        }


    }

    //显示普通页面
    private void showCommon(ArrayList<String> detail, ArrayList<String> imgurls) {
        mIntroTV.setText(detail.get(0));
        mDetailTV.setText(detail.get(1));
        Picasso.with(this).load(imgurls.get(0))
            .error(R.mipmap.bbcnews_error)
            .placeholder(R.mipmap.bbcnews_error)
            .into(mOneImgView);
        if (imgurls.size() < 2) {
            String result = "";
            for (int i = 2; i < detail.size(); i++) {
                result += detail.get(i);
            }
            TextView tv = new TextView(this);
            tv.setTextSize(16);
            tv.setText(result);
            mLinear.addView(tv);
        } else {
            int index = 0;
            for (int i = 1; i < imgurls.size(); i++) {
                index = i;
                ImageView iv = new ImageView(this);
                Picasso.with(this).load(imgurls.get(i))
                        .placeholder(R.mipmap.bbcnews_error)
                        .error(R.mipmap.bbcnews_error)
                        .into(iv);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        DensityUtil.dip2px(this, 220));
                iv.setLayoutParams(lp);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                mLinear.addView(iv);
                TextView tv = new TextView(this);
                tv.setTextSize(16);
                tv.setText(detail.get(i + 1));
                mLinear.addView(tv);
            }
            if ((index + 1) != detail.size() - 1) {
                String s = "";
                for (int j = index + 2; j < detail.size(); j++) {
                    s += detail.get(j);
                }
                TextView tv = new TextView(this);
                tv.setTextSize(16);
                tv.setText(s);
                mLinear.addView(tv);
            }
        }
    }

    //显示Vedio页面
    private void showVideo(ArrayList<String> detail) {
        Picasso.with(this).load("http://img5.imgtn.bdimg.com/it/u=2977696489,3197788028&fm=21&gp=0.jpg")
                .into(mOneImgView);
        if(detail.size() <= 1)
        {
            mIntroTV.setText(detail.get(0));
        }
        else
        {
            mIntroTV.setText(detail.get(0));
            mDetailTV.setText(detail.get(1));
            String result = "";
            for (int i = 2; i < detail.size(); i++) {
                result += detail.get(i);
            }
            TextView tv = new TextView(this);
            tv.setTextSize(16);
            tv.setText(result);
            mLinear.addView(tv);
        }
    }


    @Override
    public void showFailure(String failure) {
        mIntroTV.setVisibility(View.GONE);
        mDetailTV.setVisibility(View.GONE);
        mOneImgView.setVisibility(View.GONE);
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        tv.setLayoutParams(lp);
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
