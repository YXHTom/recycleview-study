package com.chinamall21.mobile.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/10/30 11:18.
 */


public class SizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        int dpi = displayMetrics.densityDpi;
        LogUtils.LogE("dpi = "+dpi);
        LogUtils.LogE("widthPixels ="+widthPixels +" heightPixels ="+heightPixels);
        displayMetrics.density = widthPixels/360;
        setContentView(R.layout.activity_size);
        //420 480

    }

}
