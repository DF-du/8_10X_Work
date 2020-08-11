package com.dlf.a8_10x_work.model;

import android.util.Log;

import com.dlf.a8_10x_work.utils.RxUtil;
import com.dlf.a8_10x_work.bean.Bean;
import com.dlf.a8_10x_work.utils.HttpUtil;

import io.reactivex.subscribers.ResourceSubscriber;

public class QianModel {
    private static final String TAG = "QianModel";

    public void getData(BaseCallBack<Bean> callBack) {
        HttpUtil.getInstance()
                .getApiService()
                .getData()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResourceSubscriber<Bean>() {
                    @Override
                    public void onNext(Bean bean) {
                        callBack.onSuccess(bean);
                        Log.d(TAG, "onNext: " + bean.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        callBack.onFail(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
