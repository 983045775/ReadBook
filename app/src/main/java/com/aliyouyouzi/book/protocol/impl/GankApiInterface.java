package com.aliyouyouzi.book.protocol.impl;

import com.aliyouyouzi.book.constants.GankApi;
import com.aliyouyouzi.book.model.SplashPhotoInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2016/12/27.
 */

public interface GankApiInterface {

    @GET(GankApi.ICON_START)
    Call<SplashPhotoInfo> getSplashPath();
}
