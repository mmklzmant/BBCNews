package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.TedTopPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentTopTed;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/26 9:26
 * @classname TedTopModule
 * @description Ted 模块Top 的module 提供 Presenter
 */
@Module
public class TedTopModule {
    private FragmentTopTed fragment;
    public TedTopModule(FragmentTopTed fragment)
    {
        this.fragment = fragment;
    }

    @Provides
    @SingleActivity
    FragmentTopTed provideFragment()
    {
        return fragment;
    }

    @Provides
    @SingleActivity
    TedTopPresenter providePresenter(FragmentTopTed fragment)
    {
        return new TedTopPresenter(fragment);
    }
}
