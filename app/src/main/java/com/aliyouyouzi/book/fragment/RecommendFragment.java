package com.aliyouyouzi.book.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.constants.CacheApi;
import com.aliyouyouzi.book.fragment.Base.BaseFragment;
import com.aliyouyouzi.book.model.RecommendContentInfo;
import com.aliyouyouzi.book.protocol.RecommendProtocol;
import com.aliyouyouzi.book.utils.CacheUtil;
import com.aliyouyouzi.book.utils.ToastUtils;
import com.aliyouyouzi.book.view.RefreshFrameLayout;
import com.victor.loading.rotate.RotateLoading;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by admin on 2016/12/28.
 */

public class RecommendFragment extends BaseFragment {

    public RefreshFrameLayout mRfRefresh;
    private CacheUtil mCacheUtil;
    private RecommendContentInfo mContentDatas; //正文数据,作者,内容
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvAuthor;
    private RotateLoading mLoading;

    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_content_recommend, container, false);
    }

    @Override
    protected void findview() {
        mRfRefresh = (RefreshFrameLayout) mRootView.findViewById(R.id.recommend_rf_refresh);

        mTvAuthor = (TextView) mRootView.findViewById(R.id.recommend_tv_author);
        mTvContent = (TextView) mRootView.findViewById(R.id.recommend_tv_content);
        mTvTitle = (TextView) mRootView.findViewById(R.id.recommend_tv_title);
        mLoading = (RotateLoading) mRootView.findViewById(R.id
                .recommend_newton_cradle_loading);
        mCacheUtil = CacheUtil.getInstance(getActivity());
    }

    @Override
    protected void initDatas() {
        //加载开始
        mLoading.start();

        if (!mCacheUtil.isCacheEmpty(CacheApi.RECOMMEND_CONTENT)) {
            //取缓存
            mContentDatas = (RecommendContentInfo) mCacheUtil.getAsObject(CacheApi
                    .RECOMMEND_CONTENT);
            initView();
        }
        // 获取每日推荐数据
        RecommendProtocol.getInstance().getContentDatas(new RecommendProtocol
                .StringCallback() {

            @Override
            public void onFailure(Throwable e) {
                ToastUtils.showToast("当前网络出现错误");
            }

            @Override
            public void onResponse(RecommendContentInfo result) {
                mContentDatas = result;
                if (mCacheUtil.isCacheEmpty(CacheApi.RECOMMEND_CONTENT)) {
                    //缓存为空
                    initView();
                }
                mCacheUtil.put(CacheApi.RECOMMEND_CONTENT, mContentDatas);
            }
        });
    }

    @Override
    protected void BindEvent() {
        mRfRefresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshView();
            }
        });

    }

    private void refreshView() {
        // 获取每日推荐数据
        RecommendProtocol.getInstance().getContentDatas(new RecommendProtocol
                .StringCallback() {

            @Override
            public void onFailure(Throwable e) {
                ToastUtils.showToast("当前网络出现错误");
                mRfRefresh.refreshComplete();
            }

            @Override
            public void onResponse(RecommendContentInfo result) {
                mContentDatas = result;
                mCacheUtil.put(CacheApi.RECOMMEND_CONTENT, mContentDatas);
                mRfRefresh.refreshComplete();
                initView();
            }
        });

    }

    private void initView() {
        mLoading.stop();
        mLoading.setVisibility(View.GONE);
        mTvContent.setText("        " + mContentDatas.content);
        mTvTitle.setText(mContentDatas.title);
        mTvAuthor.setText(mContentDatas.author);
    }
}
