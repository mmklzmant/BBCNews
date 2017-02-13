package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCChinaComponent;
import com.project.zmant.bbcnews.module.BBCChinaModule;
import com.project.zmant.bbcnews.presenter.BBCChinaPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/12/16 18:11
 * @classname
 * @description
 */

public class FragmentChina extends BaseMainFragment{
    @BindView(R.id.refresh_china_main)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.recycleview_china_main)
    RecyclerView mRecycle;

    @Inject
    BBCChinaPresenter mPresenter;
    @Inject
    ApiService apiService;

    public FragmentChina(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected SwipeRefreshLayout getRefresh() {
        return mRefresh;
    }

    @Override
    protected RecyclerView getRecycle() {
        return mRecycle;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerBBCChinaComponent.builder()
                .appComponent(appComponent)
                .bBCChinaModule(new BBCChinaModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        if(!isVisible)
        {
            showProgress();
            mPresenter.loadData(apiService, "/news/world/asia/china");
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
                mPresenter.loadData(apiService, "/news/world/asia/china");
            }
        });
    }
}
