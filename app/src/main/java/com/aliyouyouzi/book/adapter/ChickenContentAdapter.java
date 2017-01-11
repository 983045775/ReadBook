package com.aliyouyouzi.book.adapter;


import android.content.Context;
import android.view.View;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.controller.click.OpenItemController;
import com.aliyouyouzi.book.model.ChickenContentInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


/**
 * Created by admin on 2016/12/30.
 */

public class ChickenContentAdapter extends CommonAdapter<ChickenContentInfo> implements View
        .OnClickListener {

    private Context context;
    private String iconUrl;

    public ChickenContentAdapter(Context context, int layoutId, List<ChickenContentInfo> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ChickenContentInfo item, int position) {
        viewHolder.setText(R.id.item_chicken_content_tv_desc, item.text);
        viewHolder.setGlideImage(context, R.id.item_chicken_content_iv_icon, item.url);
        View view = viewHolder.getView(R.id.item_chicken_content_iv_icon);
        view.setTag(R.id.tag_image,item.url);
        viewHolder.setOnClickListener(R.id.item_chicken_content_iv_icon, this);
    }

    @Override
    public void onClick(View view) {
        //统一去controller管理
        OpenItemController.getInstance(mContext).openItemById(view,view.getTag(R.id.tag_image));
    }
}
