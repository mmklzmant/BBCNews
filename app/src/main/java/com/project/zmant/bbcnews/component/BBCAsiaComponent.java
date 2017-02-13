package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCAsiaModule;
import com.project.zmant.bbcnews.view.fragment.FragmentAsia;

import dagger.Component;

/**
 * @author zmant 2016/12/15 17:54
 * @classname BBCAsiaComponent
 * @description BBC Asia Fragment Component(Dagger2)
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCAsiaModule.class)
public interface BBCAsiaComponent {
    void inject(FragmentAsia fragmentAsia);
}
