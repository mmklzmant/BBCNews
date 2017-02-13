package com.project.zmant.bbcnews.presenter.ipresenter;

import com.project.zmant.bbcnews.bean.DailyCardViewBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 11:21
 * @classname IDailyPresenter
 * @description DialySentence模块 Presenter接口
 */

public interface IDailyPresenter<V> {
    void loadSuccess(ArrayList<DailyCardViewBean> datas);
    void loadFailure();
    void attachView(V view);
    void detachView();
}
