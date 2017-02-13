package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppTedComponent;
import com.project.zmant.bbcnews.component.DaggerTedTopComponent;
import com.project.zmant.bbcnews.module.TedTopModule;
import com.project.zmant.bbcnews.presenter.TedTopPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/30 17:46
 * @classname FragmentTopTed
 * @module Ted
 * @description Ted模块TopTed的Fragment
 */

public class FragmentTopTed extends BaseTedFragment {
    @BindView(R.id.recycleview_topted_ted)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_topted_ted)
    SwipeRefreshLayout mRefresh;

    @Inject
    ApiService apiService;
    @Inject
    TedTopPresenter mPresenter;

    public FragmentTopTed(Context context, int layoutId)
    {
        super(context, layoutId);
    }

    @Override
    protected RecyclerView getmRecycleView(View rootView) {
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.recycleview_topted_ted);
        return mRecycleView;
    }

    @Override
    protected SwipeRefreshLayout getRefreshView(View rootView) {
        mRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_topted_ted);
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
        DaggerTedTopComponent.builder()
                .appTedComponent(appTedComponent)
                .tedTopModule(new TedTopModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        mPresenter.loadData(apiService, "");
    }
}
