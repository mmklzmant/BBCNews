package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppTedComponent;
import com.project.zmant.bbcnews.component.DaggerTedTrendingComponent;
import com.project.zmant.bbcnews.module.TedTrendingModule;
import com.project.zmant.bbcnews.presenter.TedTrendingPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/30 17:46
 * @classname FragmentTrending
 * @module Ted
 * @description Ted模块Trending的Fragment
 */

public class FragmentTrending extends BaseTedFragment {

    @BindView(R.id.recycleview_trending_ted)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_trending_ted)
    SwipeRefreshLayout mRefresh;

    @Inject
    ApiService apiService;
    @Inject
    TedTrendingPresenter mPresenter;

    public FragmentTrending(Context context, int layoutId)
    {
        super(context, layoutId);
    }

    @Override
    protected RecyclerView getmRecycleView(View rootView) {
        return mRecycleView;
    }

    @Override
    protected SwipeRefreshLayout getRefreshView(View rootView) {
        return mRefresh;
    }

    @Override
    protected void initRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                mPresenter.loadData(apiService, "");
            }
        });
    }


    @Override
    protected void setupFragmentComponent(AppTedComponent appTedComponent) {
        DaggerTedTrendingComponent.builder()
                .appTedComponent(appTedComponent)
                .tedTrendingModule(new TedTrendingModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        mPresenter.loadData(apiService, "");
    }
}
