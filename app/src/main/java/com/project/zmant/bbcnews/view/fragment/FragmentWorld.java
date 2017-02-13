package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCWorldComponent;
import com.project.zmant.bbcnews.module.BBCWorldModule;
import com.project.zmant.bbcnews.presenter.BBCWorldPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 15:04
 * @classname FragmentWorld
 * @description MainViewPagerAdapter的Fragment
 */

public class FragmentWorld extends BaseMainFragment{

    @BindView(R.id.recycleview_world_main)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_world_main)
    SwipeRefreshLayout mRefresh;


    @Inject
    ApiService apiService;
    @Inject
    BBCWorldPresenter mPresenter;

    public FragmentWorld(Context context, int layoutId)
    {
        super(context, layoutId);
    }

    //初始化Component
    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerBBCWorldComponent.builder()
                .appComponent(appComponent)
                .bBCWorldModule(new BBCWorldModule(this))
                .build().inject(this);
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
    protected void initRefresh() {

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                mPresenter.loadData(apiService, "/news/world");
            }
        });
    }

    @Override
    public void initData()
    {
        if(!isVisible)//第一次
        {
            //加载网络数据
            showProgress();
            mPresenter.loadData(apiService, "/news/world");
        }
        else{
            //加载本地缓存
//            initViewPagerData();
        }
    }

}
