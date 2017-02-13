package com.project.zmant.bbcnews.component;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.module.TedTrendingModule;
import com.project.zmant.bbcnews.view.fragment.FragmentTrending;

import dagger.Component;

/**
 * @author zmant 2016/12/26 10:29
 * @classname TedTrendingComponent
 * @description Ted模块 Trending Component
 */
@SingleActivity
@Component(dependencies = AppTedComponent.class, modules = TedTrendingModule.class)
public interface TedTrendingComponent {
    void inject(FragmentTrending fragment);
}
