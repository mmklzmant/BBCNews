package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.TedDetailModule;
import com.project.zmant.bbcnews.view.activity.TedDetailActivity;

import dagger.Component;

/**
 * @author zmant 2016/12/28 10:32
 * @classname TedDetailComponent
 * @description Ted模块详情页面 Component
 */
@SingleActivity
@Component(dependencies = AppTedComponent.class, modules = TedDetailModule.class)
public interface TedDetailComponent {
    void inject(TedDetailActivity activity);
}
