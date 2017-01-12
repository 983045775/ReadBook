package com.aliyouyouzi.book.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.adapter.impl.CardAdapter;
import com.aliyouyouzi.book.model.WeChatCardInfo;
import com.aliyouyouzi.book.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/1/11.
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<WeChatCardInfo> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(WeChatCardInfo item) {
        mViews.add(null);
        mData.add(item);
    }
    public void removeAll() {
        mData.clear();
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_wechat_content_adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(WeChatCardInfo item, View view) {
        ImageView ivIcon = (ImageView) view.findViewById(R.id.titleTextView);
        TextView tvDesc = (TextView) view.findViewById(R.id.contentTextView);
        ImageUtil.getInstance().displayImage(item.getUrl(), ivIcon);
        tvDesc.setText(item.getDesc());
    }
}
