package com.aliyouyouzi.book;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by admin on 2016/12/27.
 */

public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //友盟统计普通统计场景类型
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //loadinglyout
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.drawable.define_error)
                .setEmptyImage(R.drawable.define_empty)
                .setNoNetworkImage(R.drawable.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40);
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
