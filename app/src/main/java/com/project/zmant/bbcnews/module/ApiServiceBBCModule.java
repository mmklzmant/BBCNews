package com.project.zmant.bbcnews.module;

import com.project.zmant.bbcnews.annotation.SingleApp;
import com.project.zmant.bbcnews.api.ApiService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author zmant 2016/12/11 13:39
 * @classname ApiServiceBBCModule
 * @description  Dagger2 提供ApiService
 */
@Module
public class ApiServiceBBCModule {
    public static final HttpUrl BASE_URL_BBC = HttpUrl.parse("http://www.bbc.com/");
    @Provides
    @SingleApp
    OkHttpClient provideOkHttpClient()
    {
        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .build();
        return okHttpClient;
    }

    @Provides
    @SingleApp
    HttpUrl provideBaseUrl()
    {
        return BASE_URL_BBC;
    }

    @Provides
    @SingleApp
    Retrofit provideRetrofit()
    {
        return new Retrofit.Builder().client(provideOkHttpClient())
                .baseUrl(provideBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @SingleApp
    ApiService provideApiService(Retrofit retrofit)
    {
        return retrofit.create(ApiService.class);
    }
}
