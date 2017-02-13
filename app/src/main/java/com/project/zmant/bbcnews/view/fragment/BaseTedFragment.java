package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.TedCardViewBean;
import com.project.zmant.bbcnews.component.AppTedComponent;
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
    private Context context;
    private int layoutId;
    private View rootView;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mRefresh;
    private TedCardViewAdapter mCardViewAdapter;
    ArrayList<TedCardViewBean> mDatas;
    ArrayList<TedCardViewBean> items;
    ArrayList<TedCardViewBean> loads;
    int count;

    public boolean isPrepared = false;
    public boolean isVisible = false;

    public BaseTedFragment(Context context, int layoutId)
    {
        this.context = context;
        this.layoutId = layoutId;
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
    private void initLoadMore() {

        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem+1 == mCardViewAdapter.getItemCount())
                {
                    count++;
                    if(count >= 2)
                    {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCardViewAdapter.changeMoreStatus(mCardViewAdapter.NO_LOAD_MORE);
                                mRecycleView.scrollToPosition(mDatas.size()-4);
                                Toast.makeText(context, "没有了...", Toast.LENGTH_SHORT).show();
                                mCardViewAdapter.changeMoreStatus(mCardViewAdapter.PULLUP_LOAD_MORE);
                            }
                        },2000);

                    }
                    else {
                        mCardViewAdapter.changeMoreStatus(mCardViewAdapter.LOADING_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCardViewAdapter.AddFooterItem(loads);
                                mCardViewAdapter.changeMoreStatus(mCardViewAdapter.PULLUP_LOAD_MORE);
                            }
                        }, 2000);
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

    @Override
    public void showData(ArrayList<TedCardViewBean> datas) {
        mDatas = datas;
        mCardViewAdapter = new TedCardViewAdapter(context, getHalfDatas(datas));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mCardViewAdapter);
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
    public void showFailure(String error) {

    }

    @Override
    public void showProgress(boolean show) {
        mRefresh.setRefreshing(show);
    }
}
