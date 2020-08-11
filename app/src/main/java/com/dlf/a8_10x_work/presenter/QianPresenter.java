package com.dlf.a8_10x_work.presenter;

import com.dlf.a8_10x_work.model.BaseCallBack;
import com.dlf.a8_10x_work.bean.Bean;
import com.dlf.a8_10x_work.model.QianModel;
import com.dlf.a8_10x_work.view.QianView;

public class QianPresenter {
    private static final String TAG = "QianPresenter";
    private QianView view;
    private QianModel model;

    public QianPresenter(QianView view) {
        this.view = view;
        model = new QianModel();
    }

    public void getData() {
        model.getData(new BaseCallBack<Bean>() {
            @Override
            public void onSuccess(Bean bean) {
                view.setData(bean);
            }

            @Override
            public void onFail(String error) {
                view.showToast(error);
            }
        });
    }
}
