package com.chinamall21.mobile.study.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.adapter.CommonAdapter;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.layoutmanager.GalleryManager1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * desc：
 * author：Created by xusong on 2019/1/28 11:34.
 */


public class GalleryFragment extends BaseFragment {
    private String[] mStrings = {"Android开发工程师", "Java开发工程师", "Php开发工程师", "GO语言开发", "IOS开发工程师",
            "人工智能开发", "前端开发", "产品经理", "大数据开发", "UI设计", "自定义LayoutManager", "FlowLayoutManager", "RecycleView"};

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        RecyclerView recyclerView= rootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GalleryManager1());
        List list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(mStrings[new Random().nextInt(mStrings.length)]);
        }
        CommonAdapter commonAdapter = new CommonAdapter(list, R.layout.item_gallery);
        recyclerView.setAdapter(commonAdapter);

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_gallery;
    }
}
