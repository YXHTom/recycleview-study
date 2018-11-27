package com.chinamall21.mobile.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chinamall21.mobile.study.adapter.RAdapter;
import com.chinamall21.mobile.study.bean.TestBean;
import com.chinamall21.mobile.study.utils.Images;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/11 11:44.
 */


public class RActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String[] mStrings = {"南京南站", "龙眠大道", "学则路", "仙鹤门", "仙鹤名苑", "紫东国际创意园!"};
    private List<TestBean> mTestBeanList;
    private RAdapter mRAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);
        mRecyclerView = findViewById(R.id.rv);
        init();

    }

    private void init() {
        mTestBeanList = new ArrayList<>();
        for (int i = 0; i < 66; i++) {
            TestBean bean = new TestBean();
            bean.setTitle("我是title" + i);

            mTestBeanList.add(bean);
        }
        List<String> list = Arrays.asList(Images.imageUrls);
        //mRecyclerView.setLayoutManager(new MyBannerManger(this,mRecyclerView));
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        mRAdapter = new RAdapter(this, mTestBeanList,mRecyclerView);

        mRecyclerView.setAdapter(mRAdapter);

    }



}
