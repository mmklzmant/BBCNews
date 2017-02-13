package com.project.zmant.bbcnews.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.component.AppComponent;
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
    private Context context;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycle;
    BBCNewsCardViewAdapter mCardViewAdapter;
    ArrayList<BBCCardViewBean> mDatas;
    ArrayList<BBCCardViewBean> items;
    ArrayList<BBCCardViewBean> loads;
    int count;

    public boolean isPrepared = false;
    public boolean isVisible = false;

    public BaseMainFragment(Context context, int layoutId)
    {
        this.context = context;
        this.layoutId = layoutId;
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
    public void showData(ArrayList<BBCCardViewBean> datas) {
        mDatas = datas;
        mCardViewAdapter = new BBCNewsCardViewAdapter(context, getHalfDatas(datas));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycle.setLayoutManager(manager);
        mRecycle.setAdapter(mCardViewAdapter);
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

    private void initLoadMore() {

        mRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                mRecycle.scrollToPosition(mDatas.size()-4);
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
}
