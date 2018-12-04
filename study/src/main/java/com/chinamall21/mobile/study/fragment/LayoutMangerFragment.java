package com.chinamall21.mobile.study.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.adapter.CommonAdapter;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.view.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/14 14:57.
 */

public class LayoutMangerFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> mList;

    public static LayoutMangerFragment newInstance() {
        LayoutMangerFragment fragment = new LayoutMangerFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new MyLayoutManager());

    }

    @Override
    public void loadData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 249; i++) {
            mList.add("String "+i);
        }
        CommonAdapter commonAdapter = new CommonAdapter(mList);
        mRecyclerView.setAdapter(commonAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_layoutmanager;
    }

}
