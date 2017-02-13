package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.model.BBCFragmentModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCFragmentPresenter;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/14 9:44
 * @classname BBCWorldPresenter
 * @description FragmentWorld Presenter层
 */

public class BBCWorldPresenter implements IBBCFragmentPresenter<IBBCWorldView> {
    private IBBCWorldView mView;
    private BBCFragmentModel mModel;
    public BBCWorldPresenter(IBBCWorldView view)
    {
        attachView(view);
        mModel = new BBCFragmentModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<BBCCardViewBean> datas) {
        mView.hideProgress();
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
    }

    @Override
    public void attachView(IBBCWorldView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
       this.mView = null;
    }

    public  void loadData(ApiService apiService, String id)
    {
        mModel.getData(apiService, id);
    }

}
