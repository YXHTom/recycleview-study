package com.chinamall21.mobile.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * desc：
 * author：Created by xusong on 2018/11/28 10:27.
 */


public class ThreeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        OneActivity.mActivity = this;
    }
}
