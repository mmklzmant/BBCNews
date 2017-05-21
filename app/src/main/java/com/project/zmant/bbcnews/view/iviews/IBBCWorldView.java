package com.project.zmant.bbcnews.view.iviews;

import com.project.zmant.bbcnews.bean.BBCCardViewBean;

import java.util.ArrayList;

/**
 * @author zmant 2016/12/14 15:20
 * @classname IBBCWorldView
 * @description BBC 模块Tablayout World  的View层
 */

public interface IBBCWorldView{
    void showData(ArrayList<BBCCardViewBean> datas, int flag);
    void showProgress();
    void hideProgress();
}
