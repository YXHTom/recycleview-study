package com.chinamall21.mobile.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chinamall21.mobile.study.utils.LogUtils;
import com.chinamall21.mobile.study.view.MyZoomView;


public class MainActivity extends AppCompatActivity {
    MyZoomView mMyZoomView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.LogE((getIntent()==null)+"");
        LogUtils.LogE((getIntent().getExtras()==null)+"");
        LogUtils.LogE(getIntent().getExtras().getString("data"));
        LogUtils.LogE(getIntent().getStringExtra("data")+"");

    }

    public void click(View view){
        Intent intent = new Intent();
        intent.putExtra("back","backData");
        setResult(24,intent);
        finish();
    }
}
