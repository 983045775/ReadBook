package com.aliyouyouzi.book.protocol.impl;


import com.aliyouyouzi.book.model.json.WeChatInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 2016/12/28.
 */

public interface WeChatInterface {

    @GET("weixin/query")
    Call<WeChatInfo> getJSON(@Query("key") String key, @Query("pno") String pno);
}
