package com.project.zmant.bbcnews.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.DailyCardViewBean;
import com.project.zmant.bbcnews.model.DailyModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IDailyPresenter;
import com.project.zmant.bbcnews.utils.ACache;
import com.project.zmant.bbcnews.view.iviews.IDailyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 11:23
 * @classname DailyPresenter
 * @description DailySentence模块 的Presenter层
 */

public class DailyPresenter implements IDailyPresenter<IDailyView> {
    private IDailyView mView;
    private DailyModel mModel;
    ACache mACache;

    public DailyPresenter(IDailyView view)
    {
        attachView(view);
        mModel = new DailyModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<DailyCardViewBean> datas) {
        mView.showProgress(false);
        mView.showData(datas, 0);
    }

    @Override
    public void loadFailure() {
        ArrayList<DailyCardViewBean> datas = new ArrayList<>();
        JSONArray array = mACache.getAsJSONArray("daily");
        if (array != null)
        {
            DailyCardViewBean bean = null;
            JSONObject obj = null;
            for (int i = 0; i < array.length(); i++)
            {
                bean = new DailyCardViewBean();
                try {
                    obj = array.getJSONObject(i);
                    bean.setAuthor(obj.get("auther").toString());
                    bean.setChText(obj.getString("chText"));
                    bean.setDescip(obj.getString("descrip"));
                    bean.setEnText(obj.getString("enText"));
                    bean.setHeader(obj.getString("header"));
                    bean.setImgurl(obj.getString("imgurl"));
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
    public void attachView(IDailyView view) {
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
