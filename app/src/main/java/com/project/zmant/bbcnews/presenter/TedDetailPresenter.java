package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.model.TedDetailModel;
import com.project.zmant.bbcnews.presenter.ipresenter.ITedDetailPresenter;
import com.project.zmant.bbcnews.view.iviews.ITedDetailView;

import java.util.Map;

/**
 * @author zmant 2016/12/28 10:15
 * @classname
 * @description Ted模块详情页面presenter层
 */

public class TedDetailPresenter implements ITedDetailPresenter<ITedDetailView> {
    private ITedDetailView mView;
    private TedDetailModel mModel;
    public TedDetailPresenter(ITedDetailView view)
    {
        attachView(view);
        mModel = new TedDetailModel(this);
    }

    @Override
    public void loadSuccess(Map<String, String> map) {
        mView.hidProgress();
        mView.showData(map);
    }

    @Override
    public void loadFailure() {
        mView.hidProgress();
        mView.showFailure("加载失败...");
    }

    @Override
    public void attachView(ITedDetailView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadData(ApiService apiService, String id)
    {
        mView.showProgress();
        mModel.getData(apiService, id);
    }
}
