package com.project.zmant.bbcnews.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.view.activity.BBCNewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zmant 2016/11/30 16:26
 * @classname BBCNewsCardViewAdapter
 * @description
 */

public class BBCNewsCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<BBCCardViewBean> mDatas;
    private Context context;
    private DataViewHolder holder;
    LayoutInflater mInflater;

    private static final int TYPE_ITEM   = 0;
    private static final int TYPE_FOOTER = 1;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE     = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE     = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;

    public BBCNewsCardViewAdapter(Context context, ArrayList<BBCCardViewBean> datas)
    {
        this.context = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount())
        {
            return TYPE_FOOTER;
        }
        else
        {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item_main,parent, false);
            return new BBCNewsCardViewAdapter.DataViewHolder(view);
        }
        else if(viewType == TYPE_FOOTER)
        {
            View footerView = mInflater.inflate(R.layout.load_more_footview_layout, parent, false);
            return new BBCNewsCardViewAdapter.FooterViewHolder(footerView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof DataViewHolder)
        {
            DataViewHolder dataViewHolder = (DataViewHolder)holder;
            Picasso.with(context).load(mDatas.get(position).getImagUrl()).into(dataViewHolder.imageView);
            dataViewHolder.titleTV.setText(mDatas.get(position).getTitle());
            dataViewHolder.timeTV.setText(mDatas.get(position).getTime());
            dataViewHolder.locationTV.setText(mDatas.get(position).getLocation());
            dataViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,
                            BBCNewsDetailActivity.class);
                    intent.putExtra("url", mDatas.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
        }
        else if(holder instanceof FooterViewHolder)
        {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus)
            {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mTvLoadText.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.mTvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:
                    footerViewHolder.mLoadLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_cardview_main)
        ImageView imageView;
        @BindView(R.id.tv_title_cardview_main)
        TextView titleTV;
        @BindView(R.id.tv_time_cardview_main)
        TextView timeTV;
        @BindView(R.id.tv_location_cardview_main)
        TextView locationTV;
        @BindView(R.id.cardview_main)
        CardView cardView;
        public DataViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pbLoad)
        ProgressBar mPbLoad;
        @BindView(R.id.tvLoadText)
        TextView     mTvLoadText;
        @BindView(R.id.loadLayout)
        LinearLayout mLoadLayout;
        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void AddHeaderItem(ArrayList<BBCCardViewBean> items) {
        mDatas.clear();
        mDatas.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(ArrayList<BBCCardViewBean> items) {
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }
}
