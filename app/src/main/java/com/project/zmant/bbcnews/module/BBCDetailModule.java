package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCDetailPresenter;
import com.project.zmant.bbcnews.view.activity.BBCNewsDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/18 16:28
 * @classname BBCDetailModule
 * @description BBC Detail Module 提供Presenter对象
 */
@Module
public class BBCDetailModule {
    private BBCNewsDetailActivity activity;
    public BBCDetailModule(BBCNewsDetailActivity activity)
    {
        this.activity = activity;
    }

    @Provides
    @SingleActivity
    BBCNewsDetailActivity provideActivity()
    {
        return activity;
    }

    @Provides
    @SingleActivity
    BBCDetailPresenter providePresenter(BBCNewsDetailActivity activity)
    {
        return new BBCDetailPresenter(activity);
    }
}
