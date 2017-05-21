package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.TedCardViewBean;
import com.project.zmant.bbcnews.component.AppTedComponent;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.view.DApplication;
import com.project.zmant.bbcnews.view.adapter.TedCardViewAdapter;
import com.project.zmant.bbcnews.view.iviews.ITedView;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author zmant 2016/11/30 16:11
 * @classname BaseTedFragment
 * @description Ted模块 BaseFragment
 */

public abstract class BaseTedFragment extends Fragment implements ITedView {
    Context context;
    private int layoutId;
    private View rootView;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mRefresh;
    TedCardViewAdapter mCardViewAdapter;
    ArrayList<TedCardViewBean> mDatas;
    ArrayList<TedCardViewBean> items;
    ArrayList<TedCardViewBean> loads;
    int count;
    ACache mACache;

    public boolean isPrepared = false;
    public boolean isVisible = false;

    public BaseTedFragment(Context context, int layoutId)
    {
        this.context = context;
        this.layoutId = layoutId;
        mACache = ACache.get(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, rootView);
        mRecycleView = getmRecycleView(rootView);
        mRefresh = getRefreshView(rootView);
        mRefresh.setColorSchemeResources( R.color.colorPrimary);
        isPrepared = true;
        setupFragmentComponent(DApplication.get(context).getAppTedComponent());
        initData();
        setListener();
        return rootView;
    }

    protected void setListener() {
        initRefresh();
        initLoadMore();
    }

    protected abstract void initRefresh();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint() && isPrepared) {
            isVisible = true;
        }
        else
        {
            isVisible = false;
        }
    }

    abstract protected RecyclerView getmRecycleView(View rootView);
    abstract protected SwipeRefreshLayout getRefreshView(View rootView);
    protected abstract void setupFragmentComponent(AppTedComponent appTedComponent);
    protected abstract void initData();
    protected abstract  void initLoadMore();
    protected abstract void setCount();
    protected abstract void storeData(ArrayList<TedCardViewBean> datas);

    @Override
    public void showData(ArrayList<TedCardViewBean> datas, int flag) {
        mDatas = datas;
        mCardViewAdapter = new TedCardViewAdapter(context, getHalfDatas(datas));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mCardViewAdapter);
        setCount();
        if(flag == 0)
        {
            storeData(datas);
        }
    }

    protected ArrayList<TedCardViewBean> getHalfDatas(ArrayList<TedCardViewBean> datas) {
        items = new ArrayList<>();
        loads = new ArrayList<>();
        for(int i = 0; i < mDatas.size(); i++)
        {
            if(i < mDatas.size()/2)
            {
                items.add(mDatas.get(i));
            }
            else
            {
                loads.add(mDatas.get(i));
            }
        }
        return items;
    }

    @Override
    public void showProgress(boolean show) {
        mRefresh.setRefreshing(show);
    }
}
