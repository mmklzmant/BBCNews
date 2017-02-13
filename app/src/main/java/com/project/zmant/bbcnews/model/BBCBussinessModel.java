package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.presenter.BBCBusinessPresenter;

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
 * @author zmant 2016/12/16 14:49
 * @classname BBCBussinessModel
 * @description BBC模块Business Model层
 */

public class BBCBussinessModel {
    private BBCBusinessPresenter mPresenter;
    public BBCBussinessModel(BBCBusinessPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    public void getData(ApiService apiService, String id)
    {
        apiService.getData(id)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, ArrayList<BBCCardViewBean>>() {

                    @Override
                    public ArrayList<BBCCardViewBean> call(String s) {
                        return parseHtml(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<BBCCardViewBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.loadFailure();
                    }

                    @Override
                    public void onNext(ArrayList<BBCCardViewBean> bbcCardViewBeen) {
                        mPresenter.loadSuccess(bbcCardViewBeen);
                    }
                });
    }

    private ArrayList<BBCCardViewBean> parseHtml(String html) {
        ArrayList<BBCCardViewBean> datas = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> imgurls = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();
        ArrayList<String> locations = new ArrayList<>();

        Document doc = Jsoup.parse(html);
        Elements eleA = doc.select("a.title-link");
        Elements eleB = doc.select("ul.mini-info-list");
        Elements eleC = doc.select("div.js-delayed-image-load");
        Elements eleD = doc.select("img.js-image-replace");
        titles = getArray(eleA.select("h3"), 0, "");
        urls = getArray(eleA, 1, "href");
        imgurls = getArray(eleC, 1, "data-src");
        times = getArray(eleB.select("div"), 0, "");
        locations = getArray(eleB.select("a"), 0, "");
        for(Element e : eleD)
        {
            imgurls.add(0, e.attr("src"));//加载第一个imgurl
        }
        datas = mergeBusiness(titles, urls, imgurls, times, locations);

        return datas;
    }

    /**
     * @param titles
     * @param urls
     * @param imgurls
     * @param times
     * @param locations
     * @return ArrayList<BBCCardViewBean>
     */
    private ArrayList<BBCCardViewBean> mergeBusiness(ArrayList<String> titles,ArrayList<String> urls,
                                                     ArrayList<String> imgurls, ArrayList<String> times, ArrayList<String> locations)
    {
        ArrayList<BBCCardViewBean> datas = new ArrayList<>();
        BBCCardViewBean bean = null;
        for(int i = 0; (i < titles.size() - 10); i++)
        {
            bean = new BBCCardViewBean();
            bean.setTitle(titles.get(i));
            bean.setUrl(urls.get(i));
            bean.setLocation(locations.get(i));
            if(i > 2 && i < 12)
            {
                bean.setImagUrl("http://img5.imgtn.bdimg.com/it/u=2977696489,3197788028&fm=21&gp=0.jpg");
            }
            else if(i <= 2)
            {
                bean.setImagUrl(imgurls.get(i));
            }
            else
            {
                bean.setImagUrl(imgurls.get(i-9));
            }
            if(i == 2)
            {
                bean.setTime("");
            }
            else
            {
                if(i < 2) {
                    bean.setTime(times.get(i));
                }
                else
                {
                    bean.setTime(times.get(i-1));
                }
            }
            datas.add(bean);
        }
        return datas;
    }

    /**
     * @param ele
     * @param flag 0表示e.text(),1表示e.attr
     * @param attr 属性值
     * @return ArrayList<String>
     */
    private ArrayList<String> getArray(Elements ele, int flag, String attr) {
        ArrayList<String> results = new ArrayList<>();
        for(Element e : ele)
        {
            if(flag == 0)
            {
                results.add(e.text());
            }
            else
            {
                results.add(e.attr(attr));
            }
        }
        return results;
    }
}
