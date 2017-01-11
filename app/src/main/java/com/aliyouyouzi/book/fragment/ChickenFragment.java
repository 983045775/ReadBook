package com.aliyouyouzi.book.fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.constants.CacheApi;
import com.aliyouyouzi.book.fragment.Base.BaseFragment;
import com.aliyouyouzi.book.adapter.ChickenContentAdapter;
import com.aliyouyouzi.book.model.ChickenContentInfo;
import com.aliyouyouzi.book.protocol.ChickenProtocol;
import com.aliyouyouzi.book.utils.CacheUtil;
import com.aliyouyouzi.book.utils.ToastUtils;
import com.aliyouyouzi.book.view.SmoothViewPager;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by admin on 2016/12/28.
 */

public class ChickenFragment extends BaseFragment implements BGARefreshLayout
        .BGARefreshLayoutDelegate {

    private ArrayList<String> imgList = new ArrayList<>(); //图片的缓存
    private ArrayList<ChickenContentInfo> contentList = new ArrayList<>(); //内容的缓存


    private SmoothViewPager mSvpViewPager;
    private CacheUtil mCacheUtil;
    private ListView mLvContent;
    private ChickenContentAdapter mContentAdapter;
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_content_chicken, container, false);
    }

    @Override
    protected void findview() {
        mLvContent = (ListView) mRootView.findViewById(R.id.chicken_lv_content);
        mCacheUtil = CacheUtil.getInstance(getActivity());
        mSvpViewPager = new SmoothViewPager(getActivity(), 300);
        mSvpViewPager.setPoinstPosition(SmoothViewPager.RIGHT);

        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) mRootView.findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(),
                true);
        refreshViewHolder.setLoadingMoreText("需要加载更多了");

        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void initDatas() {

        if (!mCacheUtil.isCacheEmpty(CacheApi.chicken_Img_Viewpager)) {
            //取缓存
            imgList = (ArrayList<String>) mCacheUtil.getAsObject(CacheApi
                    .chicken_Img_Viewpager);
            initViewPager();
        }
        if (!mCacheUtil.isCacheEmpty(CacheApi.chicken_content)) {
            contentList = (ArrayList<ChickenContentInfo>) mCacheUtil.getAsObject(CacheApi
                    .chicken_content);
            initContent();
        }
        //轮播图
        ChickenProtocol.getInstance().getImageList(new ChickenProtocol.ImgListCallback() {
            @Override
            public void onResponse(ArrayList<String> result) {
                imgList.addAll(result);
                if (mCacheUtil.isCacheEmpty(CacheApi.chicken_Img_Viewpager)) {
                    //缓存为空
                    initViewPager();
                }
                mCacheUtil.put(CacheApi.chicken_Img_Viewpager, result);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
        //下方内容
        ChickenProtocol.getInstance().getContent(1, new ChickenProtocol.ContentCallback() {
            @Override
            public void onResponse(ArrayList<ChickenContentInfo> result) {
                contentList.addAll(result);
                if (mCacheUtil.isCacheEmpty(CacheApi.chicken_content)) {
                    //缓存为空
                    initContent();
                }
                mCacheUtil.put(CacheApi.chicken_content, result);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @Override
    protected void BindEvent() {
    }

    /**
     * 更新下方内容
     */
    private void initContent() {
        //初始化内容adapter
        mContentAdapter = new ChickenContentAdapter(getActivity(), R.layout
                .item_chicken_content, contentList);
        mLvContent.addHeaderView(mSvpViewPager);
        mLvContent.setAdapter(mContentAdapter);
    }

    /**
     * 更新轮播图
     */
    private void initViewPager() {
        mSvpViewPager.setImagesUrl(imgList);
        mSvpViewPager.setOnItemClickListener(new SmoothViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        new Thread() {
            public void run() {
                SystemClock.sleep(1000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endRefreshing();
                        ToastUtils.showToast("没有最新数据啦!");
                    }
                });
            }
        }.start();

    }

    private int index = 2;

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        //下方内容
        ChickenProtocol.getInstance().getContent(index++, new ChickenProtocol.ContentCallback() {
            @Override
            public void onResponse(ArrayList<ChickenContentInfo> result) {
                contentList.addAll(result);
                mContentAdapter.notifyDataSetChanged();
                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onFailure(Throwable e) {
                mRefreshLayout.endLoadingMore();
            }
        });

        return true;
    }
}
