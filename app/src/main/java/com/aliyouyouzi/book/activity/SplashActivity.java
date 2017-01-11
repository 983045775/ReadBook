package com.aliyouyouzi.book.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.activity.Base.BaseActivity;
import com.aliyouyouzi.book.constants.CacheApi;
import com.aliyouyouzi.book.constants.GankApi;
import com.aliyouyouzi.book.model.SplashPhotoInfo;
import com.aliyouyouzi.book.utils.CacheUtil;
import com.aliyouyouzi.book.utils.ImageUtil;
import com.aliyouyouzi.book.utils.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_iv_icon)
    public ImageView mIvIcon;
    private CacheUtil mCache;


    @Override
    protected void initView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDatas() {
        mCache = CacheUtil.getInstance(this);
        //        缓存不为空加载缓存
        if (!mCache.isCacheEmpty(CacheApi.SPLASH_IMG)) {
            ImageUtil.getInstance().displayImage(mCache.getAsString(CacheApi.SPLASH_IMG), mIvIcon);
        }
        //从网络加载
        iconfromNet();
    }

    private void iconfromNet() {
        if (isNetConnect()) {
            RetrofitUtils.getInstance().getResponseString(GankApi.BASIC_URL, new RetrofitUtils
                    .StringCallback() {

                @Override
                public void onFailure(Call<SplashPhotoInfo> call, Throwable e) {
                    startToMainActivity();
                }

                @Override
                public void onResponse(Call<SplashPhotoInfo> call, String photoUrl) {
                    if (photoUrl != null) {
                        if (mCache.isCacheEmpty(CacheApi.SPLASH_IMG)) {
                            ImageUtil.getInstance().displayImage(photoUrl, mIvIcon);
                        }
                        //继续缓存图片url
                        mCache.put(CacheApi.SPLASH_IMG, photoUrl);
                    } else {
                        if (mCache.isCacheEmpty(CacheApi.SPLASH_IMG)) {
                            startToMainActivity();
                        }
                    }
                }
            });
        } else {
            startToMainActivity();
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startToMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mIvIcon.startAnimation(scaleAnimation);
    }

    /*去主页*/
    private void startToMainActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void bindEvent() {

    }
}
