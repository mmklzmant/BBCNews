package com.project.zmant.bbcnews.view;

import android.app.Application;
import android.content.Context;

import com.project.zmant.bbcnews.component.AppComponent;
import com.project.zmant.bbcnews.component.AppDailyComponent;
import com.project.zmant.bbcnews.component.AppTedComponent;
import com.project.zmant.bbcnews.component.DaggerAppComponent;
import com.project.zmant.bbcnews.component.DaggerAppDailyComponent;
import com.project.zmant.bbcnews.component.DaggerAppTedComponent;
import com.project.zmant.bbcnews.module.ApiServiceBBCModule;
import com.project.zmant.bbcnews.module.ApiServiceDailyModule;
import com.project.zmant.bbcnews.module.ApiServiceTedModule;
import com.project.zmant.bbcnews.module.AppModule;

/**
 * @author zmant 2016/12/11 17:55
 * @classname DApplication
 * @description 自定义Application
 */

public class DApplication extends Application {
    private AppComponent appComponent;
    private AppTedComponent appTedComponent;
    private AppDailyComponent appDailyComponent;

    public static DApplication get(Context context) {
        return (DApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceBBCModule(new ApiServiceBBCModule())
                .build();
        appTedComponent = DaggerAppTedComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceTedModule(new ApiServiceTedModule())
                .build();
        appDailyComponent = DaggerAppDailyComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceDailyModule(new ApiServiceDailyModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public AppTedComponent getAppTedComponent() {
        return appTedComponent;
    }
    public AppDailyComponent getAppDailyComponent()
    {
        return appDailyComponent;
    }
}
