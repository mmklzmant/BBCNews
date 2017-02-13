package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCMagazineModule;
import com.project.zmant.bbcnews.view.fragment.FragmentMagazine;

import dagger.Component;

/**
 * @author zmant 2016/12/16 17:12
 * @classname BBCMagazineComponent
 * @description BBC模块 FragmentMagazine Dagger2 Component
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCMagazineModule.class)
public interface BBCMagazineComponent {
    void inject(FragmentMagazine fragment);
}
