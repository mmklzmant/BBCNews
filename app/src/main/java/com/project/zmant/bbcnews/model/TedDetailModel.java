package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.presenter.TedDetailPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zmant 2016/12/28 10:16
 * @classname TedDetailModel
 * @description Ted模块详情页面Midel层
 */

public class TedDetailModel {
    private TedDetailPresenter mPresenter;
    public TedDetailModel(TedDetailPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    public void getData(ApiService apiService, String id)
    {
        apiService.getData(id)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Map<String, String>>() {
                    @Override
                    public Map<String, String> call(String s) {
                        return parseHtmlTedDetail(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Map<String, String> map) {
                        mPresenter.loadSuccess(map);
                    }
                });
    }

    private Map<String,String> parseHtmlTedDetail(String html) {
        Map<String, String> map = new LinkedHashMap<>();
        Document doc = Jsoup.parse(html);
        Elements ele = doc.select("p.talk-transcript__para");
        for(Element e : ele)
        {
            String key = e.getElementsByTag("data").text();
            String value = e.getElementsByTag("span").text() + "\n" + "\n";
            map.put(key, value);
        }
        return map;
    }
}
