package com.project.zmant.bbcnews.model;

import com.project.zmant.bbcnews.api.ApiService;
import com.project.zmant.bbcnews.bean.TedCardViewBean;
import com.project.zmant.bbcnews.presenter.TedTopPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
 * @author zmant 2016/12/26 10:54
 * @classname TedTopModel
 * @description Ted模块Top Model层
 */

public class TedTopModel {
    private TedTopPresenter mPresenter;

    public TedTopModel(TedTopPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    public void getData(ApiService apiService, String id)
    {
        apiService.getData(id)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, ArrayList<TedCardViewBean>>() {
                    @Override
                    public ArrayList<TedCardViewBean> call(String s) {
                        return parseHtmlTed(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<TedCardViewBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.loadFailure();
                    }

                    @Override
                    public void onNext(ArrayList<TedCardViewBean> array) {
                        mPresenter.loadSuccess(array);
                    }
                });
    }

    private ArrayList<TedCardViewBean> parseHtmlTed(String html) {
        ArrayList<TedCardViewBean> arraylist = new ArrayList<>();
        TedCardViewBean bean = null;
        JSONArray array = null;
        JSONArray arrayItems = null;
        JSONObject obj = null;
        Document doc = Jsoup.parse(html);
        Elements ele = doc.select("script");
        String result = "";
        for (Element e : ele) {
            if (e.data().startsWith("q(\"newHome1\",[{\"title\":\"Newest Talks\",")) {
                result += e.data().substring(13, e.data().length() - 20);
            }
        }
        try {
            array = new JSONArray(result);
            arrayItems = array.optJSONObject(2).getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arrayItems.length(); i++) {
            bean = new TedCardViewBean();
            obj = arrayItems.optJSONObject(i);
            try {
                bean.setTitle(obj.get("title").toString());
                bean.setImgurl(obj.get("thumb").toString());
                bean.setUrl(obj.get("url").toString());
                bean.setSpeaker(obj.get("speaker").toString());
                bean.setDescription(obj.get("speaker_description").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            arraylist.add(bean);
        }
        return arraylist;
    }
}
