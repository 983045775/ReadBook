package com.aliyouyouzi.book.protocol;

import com.aliyouyouzi.book.constants.BaseApi;
import com.aliyouyouzi.book.model.ChickenContentInfo;
import com.aliyouyouzi.book.protocol.impl.ChickenInterface;
import com.aliyouyouzi.book.utils.JsoupUtils;
import com.aliyouyouzi.book.utils.StreamTools;
import com.orhanobut.logger.Logger;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by admin on 2016/12/28.
 */

public class ChickenProtocol {

    private static ChickenProtocol instance;
    private Retrofit mRetrofit;

    public ChickenProtocol() {
    }

    public static ChickenProtocol getInstance() {
        if (instance == null) {
            instance = new ChickenProtocol();
        }
        return instance;
    }

    public void getImageList(final ImgListCallback callback) {

        String Url = BaseApi.imgUri;
        Logger.d("心灵鸡汤轮播图 uri + :---- " + Url);
        //去网络拿数据
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(Url)
                .build();

        ChickenInterface chickInterface = mRetrofit.create(ChickenInterface.class);
        chickInterface.getHTMLString().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String readString = StreamTools.readStream(response.body().byteStream());
                ArrayList<String> imgList = JsoupUtils.getInstance().praseChickImg(readString);
                callback.onResponse(imgList);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                callback.onFailure(e);
            }
        });
    }

    public void getContent(int index, final ContentCallback callback) {
        String Url = BaseApi.chickenUri;
        Logger.d("心灵鸡汤内容图 uri + :---- " + Url);
        //去网络拿数据
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(Url)
                .build();

        ChickenInterface chickInterface = mRetrofit.create(ChickenInterface.class);
        chickInterface.getChickContentString(index).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream inputStream = response.body().byteStream();
                String readString = StreamTools.readStream(inputStream);
                ArrayList<ChickenContentInfo> contentList = JsoupUtils.getInstance()
                        .praseChickContent(readString);
                callback.onResponse(contentList);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                callback.onFailure(e);
            }
        });
    }

    public interface ImgListCallback {
        void onResponse(ArrayList<String> result);

        void onFailure(Throwable e);
    }

    public interface ContentCallback {
        void onResponse(ArrayList<ChickenContentInfo> result);

        void onFailure(Throwable e);
    }
}
