package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.BBCDetailBean;
import com.project.zmant.bbcnews.presenter.BBCDetailPresenter;

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
 * @author zmant 2016/12/18 16:21
 * @classname BBCDetailModel
 * @description BBC Detail Model层
 */

public class BBCDetailModel {
    private BBCDetailPresenter mPresenter;

    public BBCDetailModel(BBCDetailPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    public void getData(ApiService apiService, String id)
    {
        apiService.getData(id)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, BBCDetailBean>() {
                    @Override
                    public BBCDetailBean call(String s) {
                        return parseHtml(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BBCDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.loadDataFailure();
                    }

                    @Override
                    public void onNext(BBCDetailBean bean) {
                        mPresenter.loadDataSuccess(bean);
                    }
                });
    }

    private BBCDetailBean parseHtml(String html)
    {
        BBCDetailBean bean = new BBCDetailBean();

        Document doc = Jsoup.parse(html);
        String title = doc.title();
        String result = "";
        Elements ele = doc.select("div.story-body__inner");
        if(ele.isEmpty())
        {
            Elements eleOther = doc.select("div.text-wrapper");
            bean = loadOther(eleOther, title);
        }
        else
        {
            bean = loadCommon(ele, title);
        }

        if(bean.getImgurls() != null) {
            if (bean.getImgurls().size() != 0) {
                bean.setParagraphs(departArray(bean.getParagraphs(),
                        (bean.getParagraphs().size() - 1) / bean.getImgurls().size()));

            }
        }
        return bean;
    }

    private BBCDetailBean loadOther(Elements ele, String title) {
        BBCDetailBean bean = new BBCDetailBean();
        ArrayList<String> imgurls = new ArrayList<>();
        ArrayList<String> paras = new ArrayList<>();
        bean.setTitle(title.substring(0, title.length()-10));
        int count = 0;

        for(Element e : ele.select("p"))
        {
            count++;

            if(e != null && count > 1)
            {
                paras.add(e.text() + "\n");
            }
        }
        bean.setParagraphs(paras);
        return bean;
    }


    private BBCDetailBean loadCommon(Elements ele, String title) {
        BBCDetailBean bean = new BBCDetailBean();
        ArrayList<String> imgurls = new ArrayList<>();
        ArrayList<String> paras = new ArrayList<>();
        bean.setTitle(title.substring(0, title.length()-10));
        for(Element e : ele.select("p"))
        {
            paras.add(e.text());
        }

        for(Element e : ele.select("img") )
        {
            if(e != null)
            {
                imgurls.add(e.attr("src"));
            }
        }
        for(Element e : ele.select("div.js-delayed-image-load"))
        {
            if(e != null)
            {
                imgurls.add(e.attr("data-src"));
            }
        }
        bean.setParagraphs(paras);
        bean.setImgurls(imgurls);
        return bean;
    }

    /**
     * 把ArrayList分为几个段落为一个String(除了第一个String)
     * @param paras
     * @return
     */
    private ArrayList<String> departArray(ArrayList<String> paras, int sizeofimgurls) {
        ArrayList<String> arrayTemp = new ArrayList<>();
        String temp = "";
        arrayTemp.add(paras.get(0));
        int reminder = (paras.size()-1)%sizeofimgurls;

        int count = 0;
        for(int i = 1; i < paras.size()-reminder; i++)
        {
            count++;
            temp += paras.get(i) + "\n" + "\n";
            if(count == sizeofimgurls)
            {
                arrayTemp.add(temp);
                count = 0;
                temp = "";
            }
        }
        if(reminder != 0)
        {
            temp = "";
            for(int i = paras.size()-reminder; i < paras.size(); i++)
            {
                temp += paras.get(i) + "\n" + "\n";
            }
            arrayTemp.add(temp);
        }

        return arrayTemp;
    }
}
