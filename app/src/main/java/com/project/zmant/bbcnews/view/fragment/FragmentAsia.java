package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.DaggerBBCAsiaComponent;
import com.project.zmant.bbcnews.module.BBCAsiaModule;
import com.project.zmant.bbcnews.presenter.BBCAsiaPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private int count = 0;
    boolean load = false;

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
            mPresenter.loadData(apiService, "/news/world/asia", context);
        }
        else{
            //加载本地缓存
//            initViewPagerData();
        }
    }

    @Override
    protected void storeData(ArrayList<BBCCardViewBean> datas) {
        BBCCardViewBean bean = null;
        JSONArray array = new JSONArray();
        JSONObject obj = null;
        for(int i = 0; i < datas.size(); i++)
        {
            obj = new JSONObject();
            bean = datas.get(i);
            try {
                obj.put("title", bean.getTitle());
                obj.put("time", bean.getTime());
                obj.put("location", bean.getLocation());
                obj.put("imgurl", bean.getImagUrl());
                obj.put("url", bean.getUrl());
                array.put(i, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mACache.put("asia", array);
        //test data is exist;
        /*String str = null;
        try {
            str = ((JSONObject)mACache.getAsJSONArray("world").get(0)).get("title").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("asia", str);
        Toast.makeText(context, str+" hello", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void initRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData(apiService, "/news/world/asia",context);
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
}
