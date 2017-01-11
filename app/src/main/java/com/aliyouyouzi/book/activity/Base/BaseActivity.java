package com.aliyouyouzi.book.activity.Base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aliyouyouzi.book.utils.NetUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by admin on 2016/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityState(this);
        initView();
        initDatas();
        bindEvent();
    }

    protected void setActivityState(Activity activity) {
        //设置 APP 只能竖屏显示
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public boolean isNetConnect() {
        return NetUtil.isNetConnect(this);
    }

    protected abstract void initView();

    protected abstract void initDatas();

    protected abstract void bindEvent();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
