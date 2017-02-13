package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCMagazinePresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentMagazine;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/16 17:05
 * @classname BBCMagazineModule
 * @description BBC模块 FragmentMagazine Dagger2 Module
 */
@Module
public class BBCMagazineModule {
    private FragmentMagazine fragmentMagazine;
    public BBCMagazineModule(FragmentMagazine magazine)
    {
        this.fragmentMagazine = magazine;
    }

    @Provides
    @SingleActivity
    FragmentMagazine provideFragment()
    {
        return fragmentMagazine;
    }

    @Provides
    @SingleActivity
    BBCMagazinePresenter providePresenter(FragmentMagazine fragment)
    {
        return new BBCMagazinePresenter(fragment);
    }
}
