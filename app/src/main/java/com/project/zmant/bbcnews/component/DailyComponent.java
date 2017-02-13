package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.DailyModule;
import com.project.zmant.bbcnews.view.activity.DailySentenceActivity;

import dagger.Component;

/**
 * @author zmant 2016/12/11 18:05
 * @classname BBCComponent
 * @description BBC模块 的 Component
 */
@SingleActivity
@Component(modules = DailyModule.class, dependencies = AppDailyComponent.class)
public interface DailyComponent {
    void inject(DailySentenceActivity mainActivity);
}
