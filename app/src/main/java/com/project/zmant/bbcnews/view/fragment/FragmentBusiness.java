package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCBusinessComponent;
import com.project.zmant.bbcnews.module.BBCBusinessModule;
import com.project.zmant.bbcnews.presenter.BBCBusinessPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 15:04
 * @classname FragmentBusiness
 * @description MainViewPagerAdapter的Fragment
 */

public class FragmentBusiness extends BaseMainFragment {
    @BindView(R.id.recycleview_business_main)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_business_main)
    SwipeRefreshLayout mRefresh;

    @Inject
    BBCBusinessPresenter mPresenter;
    @Inject
    ApiService apiService;

    public FragmentBusiness(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected SwipeRefreshLayout getRefresh() {
        return mRefresh;
    }

    @Override
    protected RecyclerView getRecycle() {
        return mRecycleView;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerBBCBusinessComponent.builder()
                .appComponent(appComponent)
                .bBCBusinessModule(new BBCBusinessModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        if(!isVisible)
        {
            showProgress();
            mPresenter.loadData(apiService, "/news/business");
        }
    }

    @Override
    protected void initRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                mPresenter.loadData(apiService, "/news/business");
            }
        });
    }
}
