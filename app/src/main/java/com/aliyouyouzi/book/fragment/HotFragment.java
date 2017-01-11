package com.aliyouyouzi.book.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliyouyouzi.book.R;

/**
 * Created by admin on 2016/12/28.
 */

public class HotFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_hot, container, false);
    }
}
