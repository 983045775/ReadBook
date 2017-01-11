package com.aliyouyouzi.book.protocol.impl;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by admin on 2016/12/28.
 */

public interface ChickenInterface {

    @GET(".")
    Call<ResponseBody> getHTMLString();
    @GET("index_{index}.html")
    Call<ResponseBody> getChickContentString(@Path("index") int index);
}
