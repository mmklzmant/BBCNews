package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.DailyCardViewBean;
import com.project.zmant.bbcnews.presenter.DailyPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zmant 2016/12/26 11:24
 * @classname DailyModel
 * @description DailySentence模块 Model层
 */

public class DailyModel {
    private DailyPresenter mPresenter;
    public DailyModel(DailyPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    public void getData(ApiService apiService, String id)
    {
        apiService.getData("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, ArrayList<DailyCardViewBean>>() {
                    @Override
                    public ArrayList<DailyCardViewBean> call(String s) {
                        return parseHtmlDaily(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<DailyCardViewBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<DailyCardViewBean> list) {
                        mPresenter.loadSuccess(list);
                    }
                });
    }

    private ArrayList<DailyCardViewBean> parseHtmlDaily(String html)
    {
        ArrayList<DailyCardViewBean> arrayList = new ArrayList<>();
        DailyCardViewBean bean = null;
        Document doc = Jsoup.parse(html);
        Elements ele = doc.select("div.post");

        for (Element e : ele)
        {
            bean = new DailyCardViewBean();
            bean.setHeader(e.getElementsByTag("h2").text());
            bean.setEnText(e.getElementsByTag("strong").text());
            bean.setAuthor(e.getElementsByAttributeValueStarting("style", "text-align: center").text());
            bean.setChText(e.getElementsByTag("p").get(1).text());
            bean.setDescip(e.getElementsByTag("li").text());
            bean.setImgurl(e.getElementsByTag("img").attr("src"));
            arrayList.add(bean);
        }
        return arrayList;
    }
}
