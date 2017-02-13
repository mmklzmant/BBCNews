package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCAsiaComponent;
import com.project.zmant.bbcnews.module.BBCAsiaModule;
import com.project.zmant.bbcnews.presenter.BBCAsiaPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 15:04
 * @classname FragmentAsia
 * @description MainViewPagerAdapter的Fragment
 */

public class FragmentAsia extends BaseMainFragment{

    @BindView(R.id.recycleview_asia_main)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_asia_main)
    SwipeRefreshLayout mRefresh;

    @Inject
    BBCAsiaPresenter mPresenter;
    @Inject
    ApiService apiService;

    public FragmentAsia(Context context, int layoutId)
    {
        super(context, layoutId);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerBBCAsiaComponent.builder()
                .appComponent(appComponent)
                .bBCAsiaModule(new BBCAsiaModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData()
    {
        if(!isVisible)//第一次
        {
            //加载网络数据
            showProgress();
            mPresenter.loadData(apiService, "/news/world/asia");
        }
        else{
            //加载本地缓存
//            initViewPagerData();
        }
    }

    @Override
    protected void initRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                mPresenter.loadData(apiService, "/news/world/asia");
            }
        });
    }

    @Override
    protected SwipeRefreshLayout getRefresh() {
        return mRefresh;
    }

    @Override
    protected RecyclerView getRecycle() {
        return mRecycleView;
    }
}
