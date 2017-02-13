package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCAsiaPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentAsia;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/15 17:49
 * @classname BBCAsiaModule
 * @description BBC Asia Module(Dagger2)
 */
@Module
public class BBCAsiaModule {
    private FragmentAsia fragment;
    public BBCAsiaModule(FragmentAsia fragment)
    {
        this.fragment = fragment;
    }

    @Provides
    @SingleActivity
    FragmentAsia provideFragment()
    {
        return fragment;
    }

    @Provides
    @SingleActivity
    BBCAsiaPresenter providePresenter(FragmentAsia fragment)
    {
        return new BBCAsiaPresenter(fragment);
    }
}
