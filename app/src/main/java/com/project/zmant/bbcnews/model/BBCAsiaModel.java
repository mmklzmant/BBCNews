package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.presenter.BBCAsiaPresenter;

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
 * @author zmant 2016/12/15 17:23
 * @classname BBCAsiaModel
 * @description BBC Asia Fragment Model层
 */

public class BBCAsiaModel {
    private BBCAsiaPresenter mPresenter;
    public BBCAsiaModel(BBCAsiaPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    public void getData(ApiService apiService, String id)
    {
        apiService.getData("/news/world/asia")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, ArrayList<BBCCardViewBean>>() {

                    @Override
                    public ArrayList<BBCCardViewBean> call(String s) {
                        return parseHtmlWorld(s);
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
                    public void onNext(ArrayList<BBCCardViewBean> datas) {
                        mPresenter.loadSuccess(datas);
                    }
                });

    }

    private ArrayList<BBCCardViewBean> parseHtmlWorld(String html) {
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
        int count = 0;
        for(Element e : eleD)
        {
            count++;
            if(count == 2)
            {
                break;
            }
            imgurls.add(0, e.attr("src"));//加载第一个imgurl
        }
        datas = mergeAsia(titles, urls, imgurls, times, locations);

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
    private ArrayList<BBCCardViewBean> mergeAsia(ArrayList<String> titles,ArrayList<String> urls,
                                                 ArrayList<String> imgurls, ArrayList<String> times, ArrayList<String> locations)
    {
        ArrayList<BBCCardViewBean> datas = new ArrayList<>();
        BBCCardViewBean bean = null;
        for(int i = 0; (i < titles.size() - 7); i++)
        {
            bean = new BBCCardViewBean();
            bean.setTitle(titles.get(i));
            bean.setUrl(urls.get(i));
            bean.setTime(times.get(i));
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
            if(i == 23 || i == 17)
            {
                bean.setLocation("");
            }
            else
            {
                if(i < 17) {
                    bean.setLocation(locations.get(i));
                }
                else if(i > 17 && i < 23)
                {
                    bean.setLocation(locations.get(i-1));
                }
                else
                {
                    bean.setLocation(locations.get(i-2));
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
