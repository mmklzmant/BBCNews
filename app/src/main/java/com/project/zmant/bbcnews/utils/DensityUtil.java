package com.project.zmant.bbcnews.utils;

import android.content.Context;

/**
 * @author zmant 2016/11/28 15:55
 * @classname DensityUtil
 * @description px 和 dip 单位转换工具
 */

public class DensityUtil {
    /**
     * 根据手机分辨率从dip转换为px
     * @param context
     * @param dipvalue
     * @return
     */
    public static int dip2px(Context context, int dipvalue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipvalue * scale + 0.5f);
    }

    /**
     * 根据手机分辨率从px转换为dip
     * @param context
     * @param pxvalue
     * @return
     */
    public static int px2dip(Context context, int pxvalue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxvalue / scale + 0.5f);
    }

}
