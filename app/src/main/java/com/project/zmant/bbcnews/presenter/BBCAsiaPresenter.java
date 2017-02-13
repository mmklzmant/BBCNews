package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.model.BBCAsiaModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCFragmentPresenter;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/15 17:22
 * @classname BBCAsiaPresenter
 * @description BBC Asia Fragment Present层
 */

public class BBCAsiaPresenter implements IBBCFragmentPresenter<IBBCWorldView> {
    private BBCAsiaModel mModel;
    private IBBCWorldView mView;

    public BBCAsiaPresenter(IBBCWorldView view)
    {
        attachView(view);
        mModel = new BBCAsiaModel(this);
    }

    @Override
    public void loadSuccess(ArrayList datas) {
        mView.hideProgress();
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
        mView.hideProgress();
    }

    @Override
    public void attachView(IBBCWorldView view) {
         this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadData(ApiService apiService, String id)
    {
        mModel.getData(apiService, id);
    }
}
