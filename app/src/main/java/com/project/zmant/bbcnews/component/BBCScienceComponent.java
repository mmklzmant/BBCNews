package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCScienceModule;
import com.project.zmant.bbcnews.view.fragment.FragmentScience;

import dagger.Component;

/**
 * @author zmant 2016/12/16 16:11
 * @classname BBCScienceComponent
 * @description BBC 模块 Science Fragment Dagger2
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCScienceModule.class)
public interface BBCScienceComponent {
    void inject(FragmentScience fragmentScience);
}
