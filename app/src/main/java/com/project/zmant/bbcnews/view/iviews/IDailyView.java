package com.project.zmant.bbcnews.view.iviews;

import com.project.zmant.bbcnews.bean.DailyCardViewBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/26 11:08
 * @classname IDailyView
 * @description DailySentence模块 View层
 */

public interface IDailyView {
    void showData(ArrayList<DailyCardViewBean> datas, int flag);
    void showProgress(boolean show);
}
