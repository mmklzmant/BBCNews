package com.project.zmant.bbcnews.utils;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zmant 2016/12/20 19:41
 * @classname ActivityManager
 * @description Activity 栈管理
 */

public class ActivityManager {

    public static List<AppCompatActivity> list = new ArrayList<AppCompatActivity>();

    public static void addActivity(AppCompatActivity activity){
        list.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity){
        list.remove(activity);
    }

    public static void finish(){

        for (AppCompatActivity activity : list) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

    }
}
