package com.chinamall21.mobile.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chinamall21.mobile.study.view.TextNumber;

/**
 * desc：
 * author：Created by xusong on 2018/9/10 14:34.
 */


public class TextNumberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textnumber);
        TextNumber textNumber = findViewById(R.id.tv1);
        textNumber.setTextNumber(500,2000);
    }
}
