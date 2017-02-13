package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.TedTrendingPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentTrending;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/26 9:26
 * @classname TedNewestModule
 * @description Ted 模块Trending 的module 提供 Presenter
 */
@Module
public class TedTrendingModule {
    private FragmentTrending fragment;
    public TedTrendingModule(FragmentTrending fragment)
    {
        this.fragment = fragment;
    }

    @Provides
    @SingleActivity
    FragmentTrending provideFragment()
    {
        return fragment;
    }

    @Provides
    @SingleActivity
    TedTrendingPresenter providePresenter(FragmentTrending fragment)
    {
        return new TedTrendingPresenter(fragment);
    }
}
