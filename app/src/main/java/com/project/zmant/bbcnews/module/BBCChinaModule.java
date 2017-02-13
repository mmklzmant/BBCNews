package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCChinaPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentChina;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/16 18:15
 * @classname BBCChinaModule
 * @description BBC模块 FragmentChina Dagger2 Module
 */
@Module
public class BBCChinaModule {
    private FragmentChina fragment;
    public BBCChinaModule(FragmentChina fragment)
    {
        this.fragment = fragment;
    }

    @Provides
    @SingleActivity
    FragmentChina provideFragment()
    {
        return fragment;
    }

    @Provides
    @SingleActivity
    BBCChinaPresenter providePresenter(FragmentChina fragment)
    {
        return new BBCChinaPresenter(fragment);
    }
}
