package com.project.zmant.bbcnews.api;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author zmant 2016/12/11 13:41
 * @classname ApiService
 * @description Retrofit 访问接口
 */

public interface ApiService {
    @GET
    Observable<String> getData(@Url String url);

}
