package com.chinamall21.mobile.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chinamall21.mobile.study.adapter.Rvadapter;
import com.chinamall21.mobile.study.bean.DataBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/8 11:05.
 */


public class RVActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Rvadapter mRvadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        initView();
        initData();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRvadapter = new Rvadapter(this));
    }

    private void initData() {
        String data = getAssetsData("city.json");
        Gson gson = new Gson();
        DataBean dataBean = gson.fromJson(data, DataBean.class);
        List<DataBean.DatasBean> list = new ArrayList<>();
        list.addAll(dataBean.getCitys());

        int count = 0;
        for (int i = 0; i < dataBean.getCitys().size(); i++) {
            if (i != 0) {
                DataBean.DatasBean bean1 = dataBean.getCitys().get(i);
                DataBean.DatasBean bean2 = dataBean.getCitys().get(i - 1);
                if (bean1.getPinyin().charAt(0) != bean2.getPinyin().charAt(0)) {
                    DataBean.DatasBean b = new DataBean.DatasBean();
                    b.setTitle(true);
                    b.setPinyin(bean1.getPinyin().charAt(0)+"");
                    list.add(i+count , b);
                    count++;
                }
            }
        }
        //mRecyclerView.addItemDecoration(new MyItemDecoration(dataBean.getCitys(), this));

        DataBean.DatasBean b = new DataBean.DatasBean();
        b.setTitle(true);
        b.setPinyin("A");
        list.add(0 , b);
        //mRecyclerView.addItemDecoration(new HeaderItemDecoration(this,list));
        mRvadapter.setDatas(list);

    }

    //从资源文件中获取分类json
    private String getAssetsData(String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", e.getMessage());
            return result;
        }
    }

}

