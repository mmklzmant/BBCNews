package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.model.BBCScienceModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IBBCFragmentPresenter;
import com.project.zmant.bbcnews.view.iviews.IBBCWorldView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/16 16:00
 * @classname BBCSciencePresenter
 * @description BBC模块Science Fagment Presenter层
 */

public class BBCSciencePresenter implements IBBCFragmentPresenter<IBBCWorldView> {
    private IBBCWorldView mView;
    private BBCScienceModel mModel;

    public BBCSciencePresenter(IBBCWorldView view)
    {
        attachView(view);
        mModel = new BBCScienceModel(this);
    }
    @Override
    public void loadSuccess(ArrayList<BBCCardViewBean> datas) {
        mView.hideProgress();
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
        mView.hideProgress();
//        mView.showData(datas); //缓存
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
