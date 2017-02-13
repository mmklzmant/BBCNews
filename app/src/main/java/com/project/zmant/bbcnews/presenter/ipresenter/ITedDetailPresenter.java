package com.project.zmant.bbcnews.presenter.ipresenter;

import java.util.Map;

/**
 * @author zmant 2016/12/18 16:16
 * @classname IBBCDetailPresenter
 * @description BBC Detail presenter接口
 */

public interface ITedDetailPresenter<V> {
    void loadSuccess(Map<String, String> map);
    void loadFailure();
    void attachView(V view);
    void detachView();
}
