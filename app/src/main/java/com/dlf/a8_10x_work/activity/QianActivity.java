package com.dlf.a8_10x_work.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dlf.a8_10x_work.adapter.RlvAdapter;
import com.dlf.a8_10x_work.bean.Bean;
import com.dlf.a8_10x_work.presenter.QianPresenter;
import com.dlf.a8_10x_work.view.QianView;
import com.dlf.a8_10x_work.R;

public class QianActivity extends AppCompatActivity implements View.OnClickListener, QianView {

    private int jin;
    private EditText et_phone;
    private EditText et_re_phone;
    private TextView tv_jin;
    private RecyclerView rlv;
    private Button btn_qu;
    private Button btn_que;
    private RlvAdapter adapter;
    private QianPresenter presenter;
    private int mCheckPosition;

    public static void startAct(Context context, String shu) {
        Intent intent = new Intent(context, QianActivity.class);
        intent.putExtra("jin", Integer.parseInt(shu));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian);
        jin = getIntent().getIntExtra("jin", 0);
        presenter = new QianPresenter(this);
        initView();
        initData();
    }

    private void initData() {
        presenter.getData();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_re_phone = (EditText) findViewById(R.id.et_re_phone);
        tv_jin = (TextView) findViewById(R.id.tv_jin);
        rlv = (RecyclerView) findViewById(R.id.rlv);
        btn_qu = (Button) findViewById(R.id.btn_qu);
        btn_que = (Button) findViewById(R.id.btn_que);
        btn_qu.setOnClickListener(this);
        btn_que.setOnClickListener(this);

        tv_jin.setText(String.valueOf(jin));

        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final ArrayList<Bean.DataBean.ListBean> list = new ArrayList<>();
        adapter = new RlvAdapter(this, list);
        rlv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qu:
                qu();
                break;
            case R.id.btn_que:
                submit();
                break;
        }
    }

    private void qu() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("是否放弃充值")
                .setPositiveButton("取消", null)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("放弃充值");
                        finish();
                    }
                }).create();
        dialog.show();
    }

    private void submit() {
        // validate
        String phone = et_phone.getText().toString().trim();
        String rePhone = et_re_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(rePhone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone.equals(rePhone)) {
            Toast.makeText(this, "两次手机号不能一致", Toast.LENGTH_SHORT).show();
            return;
        }
        mCheckPosition = adapter.mCheckPosition;
        if (mCheckPosition == -1) {
            showToast("请选啧一个条目");
            return;
        }
        final Bean.DataBean.ListBean bean = adapter.list.get(mCheckPosition);
        if (jin > bean.getSellCount()) {
            if (bean.getStockCount() > 0) {
                getExchange(bean);
            } else {
                showToast("库存不足");
            }
        } else {
            showToast("余额不足");
        }
    }

    private void getExchange(Bean.DataBean.ListBean bean) {
        int remainder = jin - bean.getSellCount() - 2;
        NumActivity.startAct(this, remainder);
    }

    @Override
    public void setData(Bean bean) {
        if (bean != null && bean.getData().getList().size() > 0) {
            adapter.addData(bean);
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
