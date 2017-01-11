package com.aliyouyouzi.book.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyouyouzi.book.R;
import com.bumptech.glide.Glide;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class RefreshHeaderView extends LinearLayout implements PtrUIHandler {

    private Context mContext;
    private View mRootView;
    private ImageView mIcon;
    private TextView mTvdesc;

    private final int REFRESH_RESET = 0;
    private final int REFRESH_DOING = 1;
    private final int REFRESH_BEGIN = 2;
    private final int REFRESH_COMPLETE = 3;

    private int STATE = 0;

    public RefreshHeaderView(Context context) {
        this(context, null);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initview();
    }

    private void initview() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.refresh_header_layout, this,
                false);
        mIcon = (ImageView) mRootView.findViewById(R.id.refresh_header_iv_icon);
        mTvdesc = (TextView) mRootView.findViewById(R.id.refresh_header_tv_desc);
        initIcon();
        addView(mRootView);
    }

    private void initIcon() {
        Glide.with(mContext).load("android.resource://com.demo.lc.refreshdemo2/drawable/tm_mui_bike").asGif()
                .into(mIcon);
    }

    /*准备下拉*/
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        STATE = REFRESH_RESET;
    }

    /*开始刷新*/
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        STATE = REFRESH_BEGIN;
    }


    /*下拉中*/
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        STATE = REFRESH_DOING;
    }

    /*刷新完成*/
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        STATE = REFRESH_COMPLETE;
    }

    /*下拉过程中位置变化*/
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status,
                                   PtrIndicator ptrIndicator) {
        //处理提醒字体
        switch (STATE) {
            case REFRESH_DOING:
                mTvdesc.setText("正在刷新...");
                break;
            case REFRESH_BEGIN:
                if (ptrIndicator.getCurrentPercent() < 1) {
                    mTvdesc.setText("下拉刷新");
                } else {
                    mTvdesc.setText("松开立即刷新");
                }
                break;
            case REFRESH_COMPLETE:
                mTvdesc.setText("加载完成...");
                break;
        }
    }
}
