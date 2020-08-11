package com.dlf.a8_10x_work.service;

import com.dlf.a8_10x_work.bean.Bean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface ApiService {
    String mUrl = "http://yun918.cn/";

    @GET("ks/jiekou1.json")
    Flowable<Bean> getData();
}
