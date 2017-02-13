package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.model.BBCBussinessModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCFragmentPresenter;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/16 14:45
 * @classname
 * @description
 */

public class BBCBusinessPresenter implements IBBCFragmentPresenter<IBBCWorldView> {
    private IBBCWorldView mView;
    private BBCBussinessModel mModel;

    public BBCBusinessPresenter(IBBCWorldView view)
    {
        attachView(view);
        mModel = new BBCBussinessModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<BBCCardViewBean> datas) {
        mView.hideProgress();
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
        mView.hideProgress();
//        mView.showData(datas);//加载缓存
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
