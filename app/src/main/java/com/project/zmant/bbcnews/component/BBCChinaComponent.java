package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCChinaModule;
import com.project.zmant.bbcnews.view.fragment.FragmentChina;

import dagger.Component;

/**
 * @author zmant 2016/12/16 18:18
 * @classname BBCChinaComponent
 * @description BBC模块 FragmentChina Dagger2 Componenet
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCChinaModule.class)
public interface BBCChinaComponent {
    void inject(FragmentChina fragmentChina);
}
