package com.aliyouyouzi.book.protocol;

import com.aliyouyouzi.book.constants.BaseApi;
import com.aliyouyouzi.book.model.RecommendContentInfo;
import com.aliyouyouzi.book.protocol.impl.RecommendInterface;
import com.aliyouyouzi.book.utils.JsoupUtils;
import com.aliyouyouzi.book.utils.StreamTools;
import com.orhanobut.logger.Logger;

import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by admin on 2016/12/28.
 */

public class RecommendProtocol {

    private static RecommendProtocol instance;
    private Retrofit mRetrofit;

    public RecommendProtocol() {
    }

    public static RecommendProtocol getInstance() {
        if (instance == null) {
            instance = new RecommendProtocol();
        }
        return instance;
    }

    public void getContentDatas(final StringCallback callback) {
        //获取不同的变化的url
        Random random = new Random();
        int i = random.nextInt(866)+475;

        String Url = BaseApi.baseUri + i + ".html/";
        Logger.d("每日一文uri + :---- " + Url);
        //去网络拿数据
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(Url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendInterface recommendInterface = mRetrofit.create(RecommendInterface.class);
        recommendInterface.getHTMLString().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                RecommendContentInfo contentInfo = null;
                try {
                    String readString = StreamTools.readStream(response.body().byteStream());
                    contentInfo = JsoupUtils.getInstance().praseRecommendContent
                            (readString);
                } catch (Exception e) {
                    contentInfo = new RecommendContentInfo();
                    contentInfo.author = "未找到";
                    contentInfo.title = "error404";
                    contentInfo.content = "抱歉,您请求的页面无法找到.也许刷新将有所帮助.";
                }
                callback.onResponse(contentInfo);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                callback.onFailure(e);
            }
        });
    }

    public interface StringCallback {
        void onFailure(Throwable e);

        void onResponse(RecommendContentInfo result);
    }
}
