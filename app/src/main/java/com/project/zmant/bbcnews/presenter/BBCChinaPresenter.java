package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.model.BBCChinaModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCFragmentPresenter;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/16 18:03
 * @classname BBCChinaPresenter
 * @description BBC模块 FragmentChina Presenter层
 */

public class BBCChinaPresenter implements IBBCFragmentPresenter<IBBCWorldView> {
    private IBBCWorldView mView;
    private BBCChinaModel mModel;

    public BBCChinaPresenter (IBBCWorldView view)
    {
        attachView(view);
        mModel = new BBCChinaModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<BBCCardViewBean> datas) {
        mView.hideProgress();
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
        mView.hideProgress();
//        mView.showData(datas);
    }

    @Override
    public void attachView(IBBCWorldView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadData(ApiService apiService, String id)
    {
        mModel.getData(apiService, id);
    }
}
