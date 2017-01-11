package com.aliyouyouzi.book.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.activity.Base.BaseActivity;
import com.aliyouyouzi.book.fragment.WeChatFragment;
import com.gastudio.gabottleloading.library.GABottleLoadingView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatWebActivity extends BaseActivity {

    private String mUrl;

    @BindView(R.id.wechat_activity_wb_webview)
    public WebView mWvWebview;
    @BindView(R.id.wechat_activity_loading_view)
    public GABottleLoadingView mloading;
    private boolean flag = true;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_we_chat_web);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDatas() {
        mloading.performAnimation();
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(WeChatFragment.ImageUrl);
        }
        initWebview();
    }

    private void initWebview() {
        mWvWebview.loadUrl(mUrl);

        WebSettings settings = mWvWebview.getSettings();
        settings.setBuiltInZoomControls(false);// 显示放大缩小按钮
        settings.setUseWideViewPort(false);// 只是双击缩放
        settings.setJavaScriptEnabled(true);// 打开js功能

        mWvWebview.setWebViewClient(new WebViewClient() {
            // 网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.d("网页开始加载");
            }

            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // <a href="tel:110">联系我们</a>
                // PhoneGap(js和本地代码互动)
                Logger.d("网页跳转:" + url);
                view.loadUrl(url);// 强制在当前页面加载网页, 不用跳浏览器
                return true;
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.d("网页加载结束");

            }
        });

        mWvWebview.setWebChromeClient(new WebChromeClient() {

            // 加载进度回调
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.d("加载进度回调:" + newProgress);
                if (newProgress == 100) {
                    if (!flag) {
                        mloading.setVisibility(View.GONE);
                    }
                    flag = false;
                }
            }

            // 网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Logger.d("标题:" + title);
            }

        });

    }

    @Override
    protected void bindEvent() {

    }
}
