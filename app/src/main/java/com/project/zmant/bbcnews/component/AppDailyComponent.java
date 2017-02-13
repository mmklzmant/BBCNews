package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleApp;
import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.module.ApiServiceDailyModule;
import com.project.zmant.bbcnews.module.AppModule;
import com.project.zmant.bbcnews.view.DApplication;

import dagger.Component;

/**
 * @author zmant 2016/12/11 17:58
 * @classname  AppDailyComponent
 * @description AppModule, ApiServiceDailyModule的Component
 */
@SingleApp
@Component(modules = {AppModule.class, ApiServiceDailyModule.class})
public interface AppDailyComponent {
    DApplication getApplication(); //AppModule
    ApiService getService(); //ApiServiceBBCModule
}
