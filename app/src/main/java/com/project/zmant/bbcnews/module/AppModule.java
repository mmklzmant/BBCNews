package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleApp;
import com.project.zmant.bbcnews.view.DApplication;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/11 17:53
 * @classname AppModule
 * @description 提供Application
 */
@Module
public class AppModule {
    private DApplication application;
    public AppModule(DApplication application)
    {
        this.application = application;
    }

    @Provides
    @SingleApp
    public DApplication provideApplication()
    {
        return application;
    }
}
