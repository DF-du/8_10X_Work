package com.dlf.a8_10x_work.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dlf.a8_10x_work.R;

public class NumActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mNumTv;

    public static void startAct(Context context, int num) {
        final Intent intent = new Intent(context, NumActivity.class);
        intent.putExtra("num", num);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num);
        initView();
    }

    private void initView() {
        final int num = getIntent().getIntExtra("num", 0);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mNumTv = (TextView) findViewById(R.id.tv_num);
        mNumTv.setText(""+num);
    }
}
