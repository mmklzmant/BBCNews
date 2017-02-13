package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCBusinessModule;
import com.project.zmant.bbcnews.view.fragment.FragmentBusiness;

import dagger.Component;

/**
 * @author zmant 2016/12/16 15:06
 * @classname BBCBusinessComponent
 * @description  BBC 模块Bussiness Dagger2 Component
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCBusinessModule.class)
public interface BBCBusinessComponent {
    void inject(FragmentBusiness fragmentBusiness);
}
