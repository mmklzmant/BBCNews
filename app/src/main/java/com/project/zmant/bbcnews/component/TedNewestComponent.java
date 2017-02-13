package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.TedNewestModule;
import com.project.zmant.bbcnews.view.fragment.FragmentNewest;

import dagger.Component;

/**
 * @author zmant 2016/12/26 10:29
 * @classname TedNewestComponent
 * @description Ted模块 Newest Component
 */
@SingleActivity
@Component(dependencies = AppTedComponent.class, modules = TedNewestModule.class)
public interface TedNewestComponent {
    void inject(FragmentNewest fragmentNewest);
}
