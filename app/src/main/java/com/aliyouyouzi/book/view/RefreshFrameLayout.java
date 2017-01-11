package com.aliyouyouzi.book.view;


import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by admin on 2016/12/24.
 */

public class RefreshFrameLayout extends PtrFrameLayout {
    private Context mContext;

    public RefreshFrameLayout(Context context) {
        this(context, null);
    }

    public RefreshFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        RefreshHeaderView headerView = new RefreshHeaderView(mContext);
        setHeaderView(headerView);
        addPtrUIHandler(headerView);
    }


}
