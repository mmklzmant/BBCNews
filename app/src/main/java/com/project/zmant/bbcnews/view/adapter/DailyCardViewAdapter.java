package com.project.zmant.bbcnews.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.zmant.bbcnews.R;
import com.project.zmant.bbcnews.bean.DailyCardViewBean;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import taobe.tec.jcc.JChineseConvertor;

/**
 * @author zmant 2016/11/30 22:53
 * @classname
 * @description
 */

public class DailyCardViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<DailyCardViewBean> mDatas;
    private ArrayList<DailyCardViewBean> datas;
    private Context context;
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


    public DailyCardViewAdapter(Context context, ArrayList<DailyCardViewBean> mDatas)
    {
        this.mDatas = mDatas;
        this.context = context;
         datas = new ArrayList<>();
        datas.add(0, mDatas.get(0));
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM)
        {
            View itemView = mInflater.inflate(R.layout.recycle_item_dailysentence, parent, false);
            return new DataViewHolder(itemView);
        }
        else if(viewType == TYPE_FOOTER)
        {
            View footerView = mInflater.inflate(R.layout.load_more_footview_layout, parent, false);
            return new FooterViewHolder(footerView);
        }
        return null;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DataViewHolder)
        {
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.tvHeader.setText(changeText(datas.get(position).getHeader()));
            Picasso.with(context).load(datas.get(position).getImgurl()).into(dataViewHolder.imageView);
            dataViewHolder.tvEnglish.setText(datas.get(position).getEnText());
            dataViewHolder.tvAuthor.setText(datas.get(position).getAuthor());
            dataViewHolder.tvChinese.setText(changeText(datas.get(position).getChText()));
            dataViewHolder.tvAuthorDescrip.setText(changeText(datas.get(position).getDescip()));
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

    private String changeText(String text)
    {
        try{
            JChineseConvertor convertor = JChineseConvertor.getInstance();
            text = convertor.t2s(text);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return text;
    }
    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_cardview_daily)
        ImageView imageView;
        @BindView(R.id.tv_header_cardview_daily)
         TextView tvHeader;
        @BindView(R.id.tv_en_cardview_dailysentence)
        TextView tvEnglish;
        @BindView(R.id.tv_author_cardview_daily)
        TextView tvAuthor;
        @BindView(R.id.tv_ch_cardview_dailysentence)
        TextView tvChinese;
        @BindView(R.id.tv_authordescrip_cardview_daily)
        TextView tvAuthorDescrip;

        public DataViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
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

    public void AddHeaderItem(int index) {
        datas.remove(0);
        datas.add(0, mDatas.get(index));
        notifyDataSetChanged();
    }

    public void AddFooterItem(int index) {
        datas.remove(0);
        datas.add(0, mDatas.get(index));
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
