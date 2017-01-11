package com.aliyouyouzi.book.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决NestedScrollView 嵌套 ListView问题
 */

public class NestedListView extends ListView {
    public NestedListView(Context context) {
        super(context);
    }

    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(60000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
