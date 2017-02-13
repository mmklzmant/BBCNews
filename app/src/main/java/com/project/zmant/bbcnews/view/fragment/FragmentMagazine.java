package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCMagazineComponent;
import com.project.zmant.bbcnews.module.BBCMagazineModule;
import com.project.zmant.bbcnews.presenter.BBCMagazinePresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 15:04
 * @classname FragmentMagazine
 * @description MainViewPagerAdapter的Fragment
 */

public class FragmentMagazine extends BaseMainFragment {
    @BindView(R.id.recycleview_magazine_main)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_magazine_main)
    SwipeRefreshLayout mRefresh;

    @Inject
    BBCMagazinePresenter mPresenter;
    @Inject
    ApiService apiService;

    public FragmentMagazine(Context context, int layoutId)
    {
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
        DaggerBBCMagazineComponent.builder()
                .appComponent(appComponent)
                .bBCMagazineModule(new BBCMagazineModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        if(!isVisible)
        {
            showProgress();
            mPresenter.loadData(apiService, "/news/magazine");
        }
        else
        {

        }
    }

    @Override
    protected void initRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                mPresenter.loadData(apiService, "/news/magazine");
            }
        });
    }

}
