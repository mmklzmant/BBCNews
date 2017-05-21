package com.project.zmant.bbcnews.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.TedCardViewBean;
import com.project.zmant.bbcnews.model.TedTopModel;
import com.project.zmant.bbcnews.presenter.ipresenter.ITedPresenter;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.view.iviews.ITedView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 10:53
 * @classname TedTopPresenter
 * @description Ted模块 Top Presenter层
 */

public class TedTopPresenter implements ITedPresenter<ITedView> {
    private ITedView mView;
    private TedTopModel mModel;
    ACache mACache;

    public TedTopPresenter(ITedView view)
    {
        attachView(view);
        mModel = new TedTopModel(this);
    }
    @Override
    public void loadSuccess(ArrayList<TedCardViewBean> datas) {
        mView.showProgress(false);
        mView.showData(datas, 0);
    }

    @Override
    public void loadFailure() {
        ArrayList<TedCardViewBean> datas = new ArrayList<>();
        JSONArray array = mACache.getAsJSONArray("top");
        if(array != null)
        {
            TedCardViewBean bean = null;
            JSONObject obj = null;
            for (int i = 0; i < array.length(); i++)
            {
                bean = new TedCardViewBean();
                try {
                    obj = array.getJSONObject(i);
                    bean.setImgurl(obj.getString("imgurl"));
                    bean.setTitle(obj.getString("title"));
                    bean.setSpeaker("Speaker");
                    bean.setDescription("Description");
                    bean.setUrl(obj.getString("url"));
                    datas.add(bean);
                    bean = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            mView.showProgress(false);
            mView.showData(datas, 1);
        }
    }

    @Override
    public void attachView(ITedView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadData(ApiService apiService, String id, Context context)
    {
        mACache = ACache.get(context);
        mView.showProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFailure();
            }
        }, 2000);
        mModel.getData(apiService, id);
    }
}
