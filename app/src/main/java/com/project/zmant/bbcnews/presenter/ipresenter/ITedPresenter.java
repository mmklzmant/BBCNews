package com.project.zmant.bbcnews.presenter.ipresenter;

import com.project.zmant.bbcnews.bean.TedCardViewBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 9:35
 * @classname ITedPresenter
 * @description Ted 模块 Presenter层接口
 */

public interface ITedPresenter<V>{
    void loadSuccess(ArrayList<TedCardViewBean> datas);
    void loadFailure();
    void attachView(V view);
    void detachView();

}
