package com.dlf.a8_10x_work;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dlf.a8_10x_work.activity.QianActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar_main;
    private EditText et_shu;
    private LinearLayout ll;
    private Button btn_dui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        et_shu = (EditText) findViewById(R.id.et_shu);
        ll = (LinearLayout) findViewById(R.id.ll);
        btn_dui = (Button) findViewById(R.id.btn_dui);
        btn_dui.setOnClickListener(this);

        toolbar_main.setTitle(" ");
        setSupportActionBar(toolbar_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dui:
                submit();
                break;
        }
    }

    private void submit() {
        String shu = et_shu.getText().toString().trim();
        if (TextUtils.isEmpty(shu)) {
            Toast.makeText(this, "输入金额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        QianActivity.startAct(this, shu);
    }
}
