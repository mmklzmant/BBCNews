package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.BBCWorldModule;
import com.project.zmant.bbcnews.view.fragment.FragmentWorld;

import dagger.Component;

/**
 * @author zmant 2016/12/15 14:57
 * @classname BBCWorldComponent
 * @description BBC World Component(Dagger2)
 */
@SingleActivity
@Component(dependencies = AppComponent.class, modules = BBCWorldModule.class)
public interface BBCWorldComponent {
    void inject(FragmentWorld fragment);
}
