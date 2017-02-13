package com.project.zmant.bbcnews.view.iviews;

import java.util.Map;

/**
 * @author zmant 2016/12/18 16:10
 * @classname IBBCDetailView
 * @description BBC News Detail View层
 */

public interface ITedDetailView {
    void showData(Map<String, String> list);
    void showFailure(String failure);
    void showProgress();
    void hidProgress();
}
