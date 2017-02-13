package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.TedTopModule;
import com.project.zmant.bbcnews.view.fragment.FragmentTopTed;

import dagger.Component;

/**
 * @author zmant 2016/12/26 10:29
 * @classname TedTopComponent
 * @description Ted模块 Top Component
 */
@SingleActivity
@Component(dependencies = AppTedComponent.class, modules = TedTopModule.class)
public interface TedTopComponent {
    void inject(FragmentTopTed fragment);
}
