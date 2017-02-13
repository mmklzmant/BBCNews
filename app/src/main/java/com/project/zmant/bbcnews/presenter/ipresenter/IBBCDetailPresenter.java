package com.project.zmant.bbcnews.presenter.ipresenter;

import com.project.zmant.bbcnews.bean.BBCDetailBean;

/**
 * @author zmant 2016/12/18 16:16
 * @classname IBBCDetailPresenter
 * @description BBC Detail presenter接口
 */

public interface IBBCDetailPresenter<V> {
    void loadDataSuccess(BBCDetailBean bean);
    void loadDataFailure();
    void attachView(V view);
    void detachView();
}
