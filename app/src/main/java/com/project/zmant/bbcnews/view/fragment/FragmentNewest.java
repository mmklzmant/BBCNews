package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppTedComponent;
import com.project.zmant.bbcnews.component.DaggerTedNewestComponent;
import com.project.zmant.bbcnews.module.TedNewestModule;
import com.project.zmant.bbcnews.presenter.TedNewestPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/30 17:46
 * @classname FragmentNewest
 * @module Ted
 * @description Ted模块Newest的Fragment
 */

public class FragmentNewest extends BaseTedFragment {

    @BindView(R.id.recycleview_newest_ted)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_newest_ted)
    SwipeRefreshLayout mRefresh;

    @Inject
    ApiService apiService;
    @Inject
    TedNewestPresenter mPresenter;

    public FragmentNewest(Context context, int layoutId)
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
        DaggerTedNewestComponent.builder()
                .appTedComponent(appTedComponent)
                .tedNewestModule(new TedNewestModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        mPresenter.loadData(apiService, "");
    }
}
