package com.aliyouyouzi.book.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.fragment.ChickenFragment;
import com.aliyouyouzi.book.fragment.HotFragment;
import com.aliyouyouzi.book.fragment.RecommendFragment;

/**
 * Created by admin on 2016/12/28.
 */

public class FragmentController {


    private AppCompatActivity mActivity;

    public Fragment mContentHot;
    public Fragment mContentRecommend;
    public Fragment mContentArticle;

    public FragmentController(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    private void hideAll(FragmentTransaction transaction) {
        if (mContentHot != null) {
            transaction.hide(mContentHot);
        }
        if (mContentRecommend != null) {
            transaction.hide(mContentRecommend);
        }
        if (mContentArticle != null) {
            transaction.hide(mContentArticle);
        }
    }

    public void changeFragments(int itemId) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        hideAll(transaction);
        switch (itemId) {
            case R.id.nav_recommend:
                //每日推荐界面
                if (mContentRecommend != null) {
                    transaction.show(mContentRecommend);
                } else {
                    mContentRecommend = new RecommendFragment();
                    transaction.add(R.id.content_home, mContentRecommend);
                }
                break;
            case R.id.nav_article:
                // 每日一文
                if (mContentArticle != null) {
                    transaction.show(mContentArticle);
                } else {
                    mContentArticle = new ChickenFragment();
                    transaction.add(R.id.content_home, mContentArticle);
                }
                break;
            case R.id.nav_hot:
                //热文界面
                if (mContentHot != null) {
                    transaction.show(mContentHot);
                } else {
                    mContentHot = new HotFragment();
                    transaction.add(R.id.content_home, mContentHot);
                }
                break;
        }
        transaction.commit();
    }
}
