package com.dlf.a8_10x_work.model;

public interface BaseCallBack<T> {
    void onSuccess(T t);

    void onFail(String error);
}
