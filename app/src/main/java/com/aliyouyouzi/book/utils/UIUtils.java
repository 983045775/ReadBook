package com.aliyouyouzi.book.utils;

import com.aliyouyouzi.book.BaseApplication;


/**
 * Created by yusheng on 2016/10/12.
 */
public class UIUtils {

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics()
                .density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics()
                .density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = BaseApplication.getContext().getResources().getDisplayMetrics
                ().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue) {
        final float fontScale = BaseApplication.getContext().getResources().getDisplayMetrics
                ().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
