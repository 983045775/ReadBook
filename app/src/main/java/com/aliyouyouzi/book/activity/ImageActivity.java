package com.aliyouyouzi.book.activity;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.activity.Base.BaseActivity;
import com.aliyouyouzi.book.controller.click.OpenItemController;
import com.aliyouyouzi.book.utils.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ImageActivity extends BaseActivity {

    private String mDatas;
    @BindView(R.id.image_iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.image_rl_container)
    RelativeLayout mRlContainer;

    @Override
    protected void initView() {
        //1.首先在setContentView()之前执行，用于告诉Window页面切换需要使用动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(com.aliyouyouzi.book.R.layout.activity_image);

        Transition explode = TransitionInflater.from(this).inflateTransition(android.R.transition
                .explode);
        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDatas() {
        if (getIntent() != null) {
            mDatas = getIntent().getStringExtra(OpenItemController.IMAGE_URL);
        }
        setPicture();
    }

    private void setPicture() {
        ImageUtil.getInstance().displayImage(mDatas, mIvIcon);
    }

    @Override
    protected void bindEvent() {

    }

    @OnClick(R.id.image_rl_container)
    public void viewsOnclick(View view) {
        switch (view.getId()) {
            case R.id.image_rl_container:
                //触屏就关闭图片显示
                finishAfterTransition();
                break;
        }
    }
}
