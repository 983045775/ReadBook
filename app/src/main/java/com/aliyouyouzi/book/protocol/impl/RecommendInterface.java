package com.aliyouyouzi.book.protocol.impl;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2016/12/28.
 */

public interface RecommendInterface {

    @GET(".")
    Call<ResponseBody> getHTMLString();
}
