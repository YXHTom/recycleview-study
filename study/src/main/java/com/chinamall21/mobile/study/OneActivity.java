package com.chinamall21.mobile.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2018/11/28 10:27.
 */

public class OneActivity extends AppCompatActivity {
    public static AppCompatActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
    }

    public void click1(View view){
        startActivity(new Intent(this,TwoActivity.class));
    }

    public void click2(View view){
        startActivity(new Intent(this,ThreeActivity.class));
    }
}
