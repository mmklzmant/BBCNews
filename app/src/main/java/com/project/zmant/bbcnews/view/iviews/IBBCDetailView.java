package com.project.zmant.bbcnews.view.iviews;

import com.project.zmant.bbcnews.bean.BBCDetailBean;

/**
 * @author zmant 2016/12/18 16:10
 * @classname IBBCDetailView
 * @description BBC News Detail View层
 */

public interface IBBCDetailView {
    void showData(BBCDetailBean bean);
    void showFailure(String failure);
    void showProgress();
    void hidProgress();
}
