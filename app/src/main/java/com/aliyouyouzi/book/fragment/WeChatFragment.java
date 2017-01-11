package com.aliyouyouzi.book.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.activity.WeChatWebActivity;
import com.aliyouyouzi.book.adapter.CardPagerAdapter;
import com.aliyouyouzi.book.constants.BaseApi;
import com.aliyouyouzi.book.controller.click.ShadowTransformer;
import com.aliyouyouzi.book.fragment.Base.BaseFragment;
import com.aliyouyouzi.book.model.WeChatCardInfo;
import com.aliyouyouzi.book.model.json.WeChatInfo;
import com.aliyouyouzi.book.model.json.WeChatInfo.ResultBean.ListBean;
import com.aliyouyouzi.book.protocol.WeChatProtocol;

import java.util.List;

/**
 * Created by admin on 2016/12/28.
 */

public class WeChatFragment extends BaseFragment {

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private List<ListBean> mDatas;
    public final static String ImageUrl = "imageurl";

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_content_wechat, container, false);
    }

    @Override
    protected void findview() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
    }

    @Override
    protected void initDatas() {

        WeChatProtocol.getInstance().getContentDatas(BaseApi.wechatKey, "1", new WeChatProtocol
                .JsonCallback() {


            @Override
            public void onResponse(WeChatInfo result) {
                mDatas = result.result.list;
                initViewPager();
                //进行缓存
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    private void initViewPager() {
        mCardAdapter = new CardPagerAdapter();
        for (ListBean info : mDatas) {
            mCardAdapter.addCardItem(new WeChatCardInfo(info.firstImg, info.title));
        }

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(1);
    }


    @Override
    protected void BindEvent() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            private float mDownY;
            private float mDownX;
            int flage = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flage = 0;
                        mDownX = event.getX();
                        mDownY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(event.getX() - mDownX) > 10 || Math.abs(event.getY() -
                                mDownY) > 10) {
                            flage = 1;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flage == 0) {
                            int index = mViewPager.getCurrentItem();
                            //进行打开具体详情页面
                            Intent intent = new Intent(getActivity(), WeChatWebActivity.class);
                            intent.putExtra(ImageUrl, mDatas.get(index).url);
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });
    }
}
