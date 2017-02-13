package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCDetailModule;
import com.project.zmant.bbcnews.view.activity.BBCNewsDetailActivity;

import dagger.Component;

/**
 * @author zmant 2016/12/18 16:48
 * @classname BBCDetailComponent
 * @description BBC Detail Component
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCDetailModule.class)
public interface BBCDetailComponent {
    void inject(BBCNewsDetailActivity activity);
}
