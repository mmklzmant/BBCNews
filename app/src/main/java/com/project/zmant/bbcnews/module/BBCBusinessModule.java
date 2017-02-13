package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleActivity;
import com.project.zmant.bbcnews.presenter.BBCBusinessPresenter;
import com.project.zmant.bbcnews.view.fragment.FragmentBusiness;

import dagger.Module;
import dagger.Provides;

/**
 * @author zmant 2016/12/16 15:01
 * @classname BBCBusinessModule
 * @description BBC Business模块的Module提供BBCBusinessPresenter(Dagger2)
 */
@Module
public class BBCBusinessModule {
    private FragmentBusiness fragmentBusiness;
    public BBCBusinessModule(FragmentBusiness fragmentBusiness)
    {
        this.fragmentBusiness = fragmentBusiness;
    }

    @Provides
    @SingleActivity
    FragmentBusiness provideFragment()
    {
        return fragmentBusiness;
    }

    @Provides
    @SingleActivity
    BBCBusinessPresenter providePresenter(FragmentBusiness fragment)
    {
        return new BBCBusinessPresenter(fragment);
    }
}
