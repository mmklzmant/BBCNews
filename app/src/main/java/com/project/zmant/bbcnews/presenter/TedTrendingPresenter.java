package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.TedCardViewBean;
import com.project.zmant.bbcnews.model.TedTrendingModel;
import com.project.zmant.bbcnews.presenter.ipresenter.ITedPresenter;
import com.project.zmant.bbcnews.view.iviews.ITedView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 10:38
 * @classname TedTrendingPresenter
 * @description Ted模块 Trending Presenter 层
 */

public class TedTrendingPresenter implements ITedPresenter<ITedView> {
    private ITedView mView;
    private TedTrendingModel mModel;

    public TedTrendingPresenter(ITedView view)
    {
        attachView(view);
        mModel = new TedTrendingModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<TedCardViewBean> datas) {
        mView.showProgress(false);
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
        mView.showProgress(false);
        mView.showFailure("加载失败...");
    }

    @Override
    public void attachView(ITedView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadData(ApiService apiService, String id)
    {
        mView.showProgress(true);
        mModel.getData(apiService, id);
    }
}
