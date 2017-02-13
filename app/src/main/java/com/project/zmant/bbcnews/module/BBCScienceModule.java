package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCSciencePresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentScience;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/16 16:07
 * @classname BBCScienceModule
 * @description BBC 模块 Science Fragment Dagger2
 */

@Module
public class BBCScienceModule {
    private FragmentScience fragmentScience;
    public BBCScienceModule(FragmentScience fragmentScience)
    {
        this.fragmentScience = fragmentScience;
    }

    @Provides
    @SingleActivity
    FragmentScience provideFragment()
    {
        return fragmentScience;
    }

    @Provides
    @SingleActivity
    BBCSciencePresenter providePresenter(FragmentScience fragment)
    {
        return new BBCSciencePresenter(fragment);
    }
}
