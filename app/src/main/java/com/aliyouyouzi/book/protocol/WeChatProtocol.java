package com.aliyouyouzi.book.protocol;

import com.aliyouyouzi.book.constants.BaseApi;
import com.aliyouyouzi.book.model.json.WeChatInfo;
import com.aliyouyouzi.book.protocol.impl.WeChatInterface;
import com.orhanobut.logger.Logger;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2016/12/28.
 */

public class WeChatProtocol {

    private static WeChatProtocol instance;
    private Retrofit mRetrofit;

    public WeChatProtocol() {
    }

    public static WeChatProtocol getInstance() {
        if (instance == null) {
            instance = new WeChatProtocol();
        }
        return instance;
    }

    public void getContentDatas(String key, int index, final JsonCallback callback) {
        //获取不同的变化的url
        Random random = new Random();
        int i = random.nextInt(866) + 475;

        String Url = BaseApi.wechatUri;
        Logger.d("微信精选uri + :---- " + Url);
        //去网络拿数据
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeChatInterface WeChatInterface = mRetrofit.create(WeChatInterface.class);
        WeChatInterface.getJSON(key, index).enqueue(new Callback<WeChatInfo>() {

            @Override
            public void onResponse(Call<WeChatInfo> call, Response<WeChatInfo> response) {
                WeChatInfo info = response.body();
                callback.onResponse(info);
            }

            @Override
            public void onFailure(Call<WeChatInfo> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface JsonCallback {
        void onFailure(Throwable e);

        void onResponse(WeChatInfo result);
    }
}
