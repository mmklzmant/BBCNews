package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCWorldPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentWorld;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/15 14:51
 * @classname BBCWorldModule
 * @description BBC  FragmentWorld Module(Dagger2)
 */
@Module
public class BBCWorldModule {
    private FragmentWorld fragment;
    public BBCWorldModule(FragmentWorld fragment)
    {
        this.fragment = fragment;
    }
    @Provides
    @SingleActivity
    FragmentWorld provideFragment()
    {
        return fragment;
    }

    @Provides
    @SingleActivity
    BBCWorldPresenter providePresenter(FragmentWorld fragment)
    {
        return new BBCWorldPresenter(fragment);
    }
}
