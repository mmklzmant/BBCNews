package com.project.zmant.bbcnews.presenter.ipresenter;

import com.project.zmant.bbcnews.bean.BBCCardViewBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/14 9:40
 * @classname IBBCFragmentPresenter
 * @description 加载ViewPager数据的Presenter层
 */

public interface IBBCFragmentPresenter<V> {
    void loadSuccess(ArrayList<BBCCardViewBean> datas);
    void loadFailure();
    void attachView(V view);
    void detachView();
}
