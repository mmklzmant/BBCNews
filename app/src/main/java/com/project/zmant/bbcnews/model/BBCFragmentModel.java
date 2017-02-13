package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCCardViewBean;
import com.project.zmant.bbcnews.presenter.BBCWorldPresenter;

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
 * @author zmant 2016/12/14 10:07
 * @classname BBCFragmentModel
 * @description BBC ViewPager加载数据Model层
 */

public class BBCFragmentModel {
    private BBCWorldPresenter mPresenter;
    public BBCFragmentModel(BBCWorldPresenter presenter)
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
        datas = merge(titles, urls, imgurls, times, locations);
        /* String result = "";
        for(String str : titles)
        {
            result += str + "\n";
        }*/

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

    /**
     * @param titles
     * @param urls
     * @param imgurls
     * @param times
     * @param locations
     * @return ArrayList<BBCCardViewBean>
     */
    private ArrayList<BBCCardViewBean> merge(ArrayList<String> titles,ArrayList<String> urls,
                                             ArrayList<String> imgurls, ArrayList<String> times, ArrayList<String> locations)
    {
        ArrayList<BBCCardViewBean> datas = new ArrayList<>();
        BBCCardViewBean bean = null;
        for(int i = 0; (i < titles.size() - 6); i++)
        {
            bean = new BBCCardViewBean();
            bean.setTitle(titles.get(i));
            bean.setUrl(urls.get(i));
            bean.setTime(times.get(i));
            if(i > 2 && i < 12)
            {
//                bean.setImagUrl("http://static.bbci.co.uk/news/1.166.01137/apple-touch-icon.png");
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
            if(i > 24)
            {
                bean.setLocation("");
            }
            else
            {
                bean.setLocation(locations.get(i));
            }
            datas.add(bean);
        }
        return datas;
    }
}
