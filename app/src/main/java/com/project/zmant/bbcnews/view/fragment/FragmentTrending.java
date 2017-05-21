package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.TedCardViewBean;
import com.project.zmant.bbcnews.component.AppTedComponent;
import com.project.zmant.bbcnews.component.DaggerTedTrendingComponent;
import com.project.zmant.bbcnews.module.TedTrendingModule;
import com.project.zmant.bbcnews.presenter.TedTrendingPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private int count = 0;
    boolean load = false;

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
                mPresenter.loadData(apiService, "", context);
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
        mPresenter.loadData(apiService, "", context);
    }

    @Override
    protected void initLoadMore() {
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem+1 == mCardViewAdapter.getItemCount())
                {
                    count++;
                    if(load && count < 3)
                    {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCardViewAdapter.AddFooterItem(items);
                                load = false;
                                mCardViewAdapter.changeMoreStatus(mCardViewAdapter.PULLUP_LOAD_MORE);
                            }
                        },2000);

                    }
                    else if(!load && count < 3){
                        mCardViewAdapter.changeMoreStatus(mCardViewAdapter.LOADING_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCardViewAdapter.AddFooterItem(loads);
                                load = true;
                                mCardViewAdapter.changeMoreStatus(mCardViewAdapter.PULLUP_LOAD_MORE);
                            }
                        }, 2000);
                    }
                    else {
                        mCardViewAdapter.changeMoreStatus(mCardViewAdapter.NO_LOAD_MORE);
                        Toast.makeText(context, "没有了...", Toast.LENGTH_SHORT).show();
                        mCardViewAdapter.changeMoreStatus(mCardViewAdapter.PULLUP_LOAD_MORE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected void setCount() {
        this.count = 0;
    }

    @Override
    protected void storeData(ArrayList<TedCardViewBean> datas) {
        JSONArray array = new JSONArray();
        JSONObject obj = null;
        TedCardViewBean bean = null;
        for (int i = 0; i < datas.size(); i++)
        {
            bean = datas.get(i);
            obj = new JSONObject();
            try {
                obj.put("imgurl", bean.getImgurl());
                obj.put("title", bean.getTitle());
                obj.put("descrip", bean.getDescription());
                obj.put("speaker", bean.getSpeaker());
                obj.put("url", bean.getUrl());
                array.put(i, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mACache.put("trending", array);
    }
}
