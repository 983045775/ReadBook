package com.aliyouyouzi.book.utils;


import com.aliyouyouzi.book.model.SplashPhotoInfo;
import com.aliyouyouzi.book.protocol.impl.GankApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2016/12/27.
 */

public class RetrofitUtils {

    private static RetrofitUtils instance;
    private Retrofit mRetrofit;

    private RetrofitUtils() {
    }

    public static synchronized RetrofitUtils getInstance() {
        if (instance == null) {
            instance = new RetrofitUtils();
        }
        return instance;
    }

    public void getResponseString(String url, final StringCallback result) {
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankApiInterface gankProtocol = mRetrofit.create(GankApiInterface.class);
        gankProtocol.getSplashPath().enqueue(new Callback<SplashPhotoInfo>() {
            @Override
            public void onResponse(Call<SplashPhotoInfo> call, Response<SplashPhotoInfo> response) {
                SplashPhotoInfo info = response.body();
                result.onResponse(call, info.getImg());
            }

            @Override
            public void onFailure(Call<SplashPhotoInfo> call, Throwable e) {
                result.onFailure(call, e);
            }
        });


    }

    public interface StringCallback {
        void onFailure(retrofit2.Call<SplashPhotoInfo> call, Throwable e);

        void onResponse(retrofit2.Call<SplashPhotoInfo> call, String photoUrl);
    }
}
