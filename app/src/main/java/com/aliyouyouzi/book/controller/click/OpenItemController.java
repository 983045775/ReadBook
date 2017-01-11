package com.aliyouyouzi.book.controller.click;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.activity.ImageActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2017/1/10.
 */

public class OpenItemController<T extends Object> {

    private static OpenItemController mController;
    private static Context mContext;
    public static final String IMAGE_URL = "image_url";


    private OpenItemController() {
    }

    public static OpenItemController getInstance(Context context) {
        mContext = context;
        if (mController == null) {
            synchronized (OpenItemController.class) {
                if (mController == null) {
                    mController = new OpenItemController();
                }
            }
        }
        return mController;
    }

    public void openItemById(View view, Object data) {
        switch (view.getId()) {
            case R.id.item_chicken_content_iv_icon:
                //执行点击图片任务
                String url = (String) data;
                Logger.d("点击心灵鸡汤栏目 图片 ...." + url);

                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra(IMAGE_URL,url);
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) mContext, view, "sharedView").toBundle());
                break;
        }
    }
}
