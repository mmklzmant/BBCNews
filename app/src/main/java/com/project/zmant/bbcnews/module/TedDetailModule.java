package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.TedDetailPresenter;
import com.project.zmant.bbcnews.view.activity.TedDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/28 10:29
 * @classname
 * @description Ted 模块详情页面 Module
 */
@Module
public class TedDetailModule {
    private TedDetailActivity activity;

    public TedDetailModule(TedDetailActivity activity)
    {
        this.activity = activity;
    }

    @Provides
    @SingleActivity
    TedDetailActivity provideActivity()
    {
        return activity;
    }

    @Provides
    @SingleActivity
    TedDetailPresenter providePresenter(TedDetailActivity activity)
    {
        return new TedDetailPresenter(activity);
    }
}
