package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.view.DApplication;
import com.project.zmant.bbcnews.view.adapter.BBCNewsCardViewAdapter;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author zmant 2016/12/16 10:16
 * @classname  BaseMainFragment
 * @description BBC 模块的BaseFragment
 */

public abstract class BaseMainFragment extends Fragment implements IBBCWorldView {
    private int layoutId;
    Context context;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycle;
    BBCNewsCardViewAdapter mCardViewAdapter;
    ArrayList<BBCCardViewBean> mDatas;
    ArrayList<BBCCardViewBean> items;
    ArrayList<BBCCardViewBean> loads;

    ACache mACache;

    public boolean isPrepared = false;
    public boolean isVisible = false;

    public BaseMainFragment(Context context, int layoutId)
    {
        this.context = context;
        this.layoutId = layoutId;
        mACache = ACache.get(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, view);
        mRefresh = getRefresh();
        mRefresh.setColorSchemeResources( R.color.colorPrimary);
        mRecycle = getRecycle();
        isPrepared = true;
        setupFragmentComponent(DApplication.get(context).getAppComponent());
        initData();
        setListener();
        return view;
    }

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


    @Override
    public void showData(ArrayList<BBCCardViewBean> datas, int flag) {
        mDatas = datas;
        mCardViewAdapter = new BBCNewsCardViewAdapter(context, getHalfDatas(datas));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycle.setLayoutManager(manager);
        mRecycle.setAdapter(mCardViewAdapter);
        setCount();
        if(flag == 0)
        {
            storeData(datas);
        }
    }

    protected ArrayList<BBCCardViewBean> getHalfDatas(ArrayList<BBCCardViewBean> datas) {
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
    protected void setListener() {
        initRefresh();
        initLoadMore();
    }

    protected abstract void initRefresh();

    @Override
    public void showProgress() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
       mRefresh.setRefreshing(false);
    }

    protected abstract SwipeRefreshLayout getRefresh();
    protected abstract RecyclerView getRecycle();
    protected abstract void setupFragmentComponent(AppComponent appComponent);
    protected abstract void initData();
    protected abstract void storeData(ArrayList<BBCCardViewBean> datas);
    protected abstract  void initLoadMore();
    protected abstract void setCount();
}
