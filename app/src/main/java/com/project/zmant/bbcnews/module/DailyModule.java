package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.DailyPresenter;
import com.project.zmant.bbcnews.view.activity.DailySentenceActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/11 18:01
 * @classname BBCModule
 * @description BBC模块的Module
 */
@Module
public class DailyModule {
    private DailySentenceActivity mainActivity;

    public DailyModule(DailySentenceActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Provides
    @SingleActivity
    DailySentenceActivity provideActivity()
    {
        return mainActivity;
    }

    @Provides
    @SingleActivity
    DailyPresenter providePresent(DailySentenceActivity mainActivity)
    {
        return new DailyPresenter(mainActivity);
    }
}
