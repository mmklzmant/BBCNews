package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCDetailBean;
import com.project.zmant.bbcnews.model.BBCDetailModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCDetailPresenter;
import com.project.zmant.bbcnews.view.iviews.IBBCDetailView;

/**
 * @author zmant 2016/12/18 16:19
 * @classname
 * @description
 */

public class BBCDetailPresenter implements IBBCDetailPresenter<IBBCDetailView> {
    private IBBCDetailView mView;
    private BBCDetailModel mModel;

    public BBCDetailPresenter(IBBCDetailView view)
    {
        attachView(view);
        this.mModel = new BBCDetailModel(this);
    }

    @Override
    public void loadDataSuccess(BBCDetailBean bean) {
        mView.hidProgress();
        mView.showData(bean);
    }

    @Override
    public void loadDataFailure() {
        mView.hidProgress();
        mView.showFailure("加载失败...");
    }

    @Override
    public void attachView(IBBCDetailView view) {
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
