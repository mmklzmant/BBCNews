package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.TedNewestPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentNewest;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/26 9:26
 * @classname TedNewestModule
 * @description Ted 模块 的module 提供 Presenter
 */
@Module
public class TedNewestModule {
    private FragmentNewest fragment;
    public TedNewestModule(FragmentNewest fragment)
    {
        this.fragment = fragment;
    }

    @Provides
    @SingleActivity
    FragmentNewest provideFragment()
    {
        return fragment;
    }

    @Provides
    @SingleActivity
    TedNewestPresenter providePresenter(FragmentNewest fragment)
    {
        return new TedNewestPresenter(fragment);
    }
}
