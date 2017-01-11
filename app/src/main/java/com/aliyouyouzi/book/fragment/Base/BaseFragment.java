package com.aliyouyouzi.book.fragment.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2016/12/28.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = getRootView(inflater, container);
        findview();
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDatas();
        BindEvent();
    }

    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container);

    protected abstract void findview();

    protected abstract void initDatas();

    protected abstract void BindEvent();
}
