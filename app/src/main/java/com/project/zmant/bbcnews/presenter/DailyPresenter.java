package com.project.zmant.bbcnews.presenter;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.DailyCardViewBean;
import com.project.zmant.bbcnews.model.DailyModel;
import com.project.zmant.bbcnews.presenter.ipresenter.IDailyPresenter;
import com.project.zmant.bbcnews.view.iviews.IDailyView;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 11:23
 * @classname DailyPresenter
 * @description DailySentence模块 的Presenter层
 */

public class DailyPresenter implements IDailyPresenter<IDailyView> {
    private IDailyView mView;
    private DailyModel mModel;
    public DailyPresenter(IDailyView view)
    {
        attachView(view);
        mModel = new DailyModel(this);
    }

    @Override
    public void loadSuccess(ArrayList<DailyCardViewBean> datas) {
        mView.showProgress(false);
        mView.showData(datas);
    }

    @Override
    public void loadFailure() {
        mView.showProgress(false);
        mView.showFailure("加载失败...");
    }

    @Override
    public void attachView(IDailyView view) {
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
