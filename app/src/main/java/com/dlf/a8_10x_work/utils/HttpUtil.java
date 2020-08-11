package com.dlf.a8_10x_work.utils;

import android.util.Log;

import com.dlf.a8_10x_work.service.ApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static volatile HttpUtil sHttpUtil = null;
    private Retrofit.Builder builder;

    private HttpUtil() {
        OkHttpClient okHttpClient = initOkHttp();
        getRetrofit(okHttpClient);
    }

    private void getRetrofit(OkHttpClient client) {
        builder = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    volatile ApiService mApiService;

    public ApiService getApiService() {
        if (mApiService == null) {
            synchronized (ApiService.class) {
                if (mApiService == null) {
                    mApiService = builder
                            .baseUrl(ApiService.mUrl)
                            .build()
                            .create(ApiService.class);
                }
            }
        }
        return mApiService;
    }

    private OkHttpClient initOkHttp() {
        final OkHttpClient build = new OkHttpClient.Builder()
                .addInterceptor(new LoggInterceptor())
                .build();
        return build;
    }

    public static HttpUtil getInstance() {
        if (sHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (sHttpUtil == null) {
                    sHttpUtil = new HttpUtil();
                }
            }
        }
        return sHttpUtil;
    }

    private class LoggInterceptor implements Interceptor {
        String TAG = "LoggInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            long startTime = System.currentTimeMillis();
            final Response proceed = chain.proceed(request);
            long stopTime = System.currentTimeMillis();
            Log.d(TAG, String.format("url：%s%nbody：%s%n耗时：%s",
                    request.url(),
                    proceed.peekBody(1024 * 1024).string(),
                    (stopTime - startTime) + "ms"));
            return proceed;
        }
    }
}
