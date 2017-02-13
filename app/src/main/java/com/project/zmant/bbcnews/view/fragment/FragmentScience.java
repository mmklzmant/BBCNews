package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCScienceComponent;
import com.project.zmant.bbcnews.module.BBCScienceModule;
import com.project.zmant.bbcnews.presenter.BBCSciencePresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author zmant 2016/11/29 15:04
 * @classname FragmentScience
 * @description MainViewPagerAdapter的Fragment
 */

public class FragmentScience extends BaseMainFragment {
    @BindView(R.id.recycleview_science_main)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_science_main)
    SwipeRefreshLayout mRefresh;

    @Inject
    BBCSciencePresenter mPresenter;
    @Inject
    ApiService apiService;

    public FragmentScience(Context context, int layoutId)
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
        DaggerBBCScienceComponent.builder()
                .appComponent(appComponent)
                .bBCScienceModule(new BBCScienceModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
         if(!isVisible)
         {
             showProgress();
             mPresenter.loadData(apiService, "/news/science_and_environment");
         }
        else
         {
             //缓存
         }
    }

    @Override
    protected void initRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                mPresenter.loadData(apiService, "/news/science_and_environment");
            }
        });
    }

}
