package com.chinamall21.mobile.study.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.adapter.CommonAdapter;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.layoutmanager.FlowLayoutManger;
import com.chinamall21.mobile.study.layoutmanager.FlowLayoutMangerRecycled;
import com.chinamall21.mobile.study.layoutmanager.FlowLayoutMangerRecycledAnim;
import com.chinamall21.mobile.study.layoutmanager.HorizontalManager;
import com.chinamall21.mobile.study.layoutmanager.HorizontalManagerRecycled;
import com.chinamall21.mobile.study.layoutmanager.HorizontalManagerRecycledAnim;
import com.chinamall21.mobile.study.layoutmanager.VerticalLayoutManger;
import com.chinamall21.mobile.study.layoutmanager.VerticalLayoutRecycledAnimManger;
import com.chinamall21.mobile.study.layoutmanager.VerticalLayoutRecycledManger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * desc：
 * author：Created by xusong on 2018/11/14 14:57.
 */

public class MyLayoutMangerFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private int mResId;
    private String[] mStrings = {"Android开发工程师", "Java开发工程师", "Php开发工程师", "GO语言开发", "IOS开发工程师",
            "人工智能开发", "前端开发", "产品经理", "大数据开发", "UI设计", "自定义LayoutManager", "FlowLayoutManager", "RecycleView"};


    public static MyLayoutMangerFragment newInstance(int type) {
        MyLayoutMangerFragment fragment = new MyLayoutMangerFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView(View rootView) {
        int type = getArguments().getInt("type");
        mRecyclerView = rootView.findViewById(R.id.rv);
        switch (type) {
            case 1:
                mResId = R.layout.item_tv;
                mRecyclerView.setLayoutManager(new VerticalLayoutManger());
                break;
            case 2:
                mResId = R.layout.item_tv;
                mRecyclerView.setLayoutManager(new VerticalLayoutRecycledManger());
                break;
            case 3:
                mResId = R.layout.item_tv;
                mRecyclerView.setLayoutManager(new VerticalLayoutRecycledAnimManger());
                break;
            case 4:
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
                mResId = R.layout.item_tv_horizontal;
                mRecyclerView.setLayoutManager(new HorizontalManager());
                break;
            case 5:
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
                mResId = R.layout.item_tv_horizontal;
                mRecyclerView.setLayoutManager(new HorizontalManagerRecycled());
                break;
            case 6:
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
                mResId = R.layout.item_tv_horizontal;
                mRecyclerView.setLayoutManager(new HorizontalManagerRecycledAnim());
                break;
            case 7:
                mResId = R.layout.item_tv_flow;
                mRecyclerView.setLayoutManager(new FlowLayoutManger());
                break;
            case 8:
                mResId = R.layout.item_tv_flow;
                mRecyclerView.setLayoutManager(new FlowLayoutMangerRecycled());
                break;
            case 9:
                mResId = R.layout.item_tv_flow;
                mRecyclerView.setLayoutManager(new FlowLayoutMangerRecycledAnim());
                break;
        }
    }



    @Override
    public void loadData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add(mStrings[new Random().nextInt(mStrings.length)]);
        }

        CommonAdapter commonAdapter = new CommonAdapter(mList, mResId);
        mRecyclerView.setAdapter(commonAdapter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mylayoutmanager;
    }

}
