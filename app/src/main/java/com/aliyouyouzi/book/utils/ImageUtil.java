package com.aliyouyouzi.book.utils;

import android.content.Context;
import android.widget.ImageView;

import com.aliyouyouzi.book.BaseApplication;
import com.aliyouyouzi.book.R;
import com.bumptech.glide.Glide;


/**
 * Created by joker on 2016/8/5.
 */
public class ImageUtil {
    private static ImageUtil instance = null;

    private ImageUtil() {
    }

    public static synchronized ImageUtil getInstance() {
        if (instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }

    //显示图片
    public void displayImage(String url, ImageView view) {
        Glide.with(BaseApplication.getContext()).load(url).placeholder(R.drawable
                .download_defualt).crossFade().error(R
                .drawable.download_defualt).into(view);
    }

    //清理缓存
    public void clear(Context context) {
        Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }
}
