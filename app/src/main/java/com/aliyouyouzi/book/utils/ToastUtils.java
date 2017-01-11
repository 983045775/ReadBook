package com.aliyouyouzi.book.utils;

import android.content.Context;
import android.widget.Toast;

import com.aliyouyouzi.book.BaseApplication;

/**
 * Created by admin on 2016/12/29.
 */

public class ToastUtils {

    public static final int TIME_SHORT = Toast.LENGTH_SHORT;
    public static final int TIME_LONG = Toast.LENGTH_LONG;

    private static Toast mToast;
    private static Context mContext = BaseApplication.getContext();

    public static void showToast(int resId) {
        showToast(mContext.getResources().getString(resId));
    }

    public static void showToast(String text) {
        showToast(text, TIME_SHORT);
    }

    public static void showToast(int resId, int duration) {
        showToast(mContext.getResources().getString(resId), duration);
    }

    public static void showToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
