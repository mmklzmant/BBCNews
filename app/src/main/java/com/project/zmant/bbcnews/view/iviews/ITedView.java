package com.project.zmant.bbcnews.view.iviews;

import com.project.zmant.bbcnews.bean.TedCardViewBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 9:42
 * @classname
 * @description Ted 模块 View层
 */

public interface ITedView {
    void showData(ArrayList<TedCardViewBean> datas, int flag);
    void showProgress(boolean show);
}
