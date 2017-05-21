package com.project.zmant.bbcnews.presenter;

import android.content.Context;
import android.os.Handler;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.model.BBCBussinessModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCFragmentPresenter;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/16 14:45
 * @classname
 * @description
 */

public class BBCBusinessPresenter implements IBBCFragmentPresenter<IBBCWorldView> {
    private IBBCWorldView mView;
    private BBCBussinessModel mModel;
    ACache mACache;

    public BBCBusinessPresenter(IBBCWorldView view)
    {
        attachView(view);
        mModel = new BBCBussinessModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<BBCCardViewBean> datas) {
        mView.hideProgress();
        mView.showData(datas,0);
    }

    @Override
    public void loadFailure() {
        ArrayList<BBCCardViewBean> datas = new ArrayList<>();
        JSONArray array = mACache.getAsJSONArray("business");
        if(array != null)
        {
            JSONObject obj = null;
            BBCCardViewBean bean = null;
            for(int i = 0; i < array.length(); i++)
            {
                bean = new BBCCardViewBean();
                try {
                    obj = (JSONObject) array.get(i);
                    bean.setTitle(obj.get("title").toString());
                    bean.setTime(obj.get("time").toString());
                    bean.setImagUrl(obj.get("imgurl").toString());
                    bean.setLocation(obj.get("location").toString());
                    bean.setUrl(obj.get("url").toString());
                    datas.add(bean);
                    bean = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mView.hideProgress();
            mView.showData(datas,1);
        }
    }

    @Override
    public void attachView(IBBCWorldView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadData(ApiService apiService, String id, Context context)
    {
        mACache = ACache.get(context);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFailure();
            }
        }, 2000);
        mModel.getData(apiService, id);
    }
}
