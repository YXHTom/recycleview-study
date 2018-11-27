package com.chinamall21.mobile.study.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chinamall21.mobile.study.App;
import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.adapter.CommonAdapter;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.view.ReFreshParent;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/8 15:57.
 */


public class PullRefreshFragment extends BaseFragment
{

    private RecyclerView mRecyclerView;
    private ReFreshParent mReFreshParent;
    private CommonAdapter mCommonAdapter;
    private List<String> mList;

    public static PullRefreshFragment newInstance() {
        return new PullRefreshFragment();
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv);
        mReFreshParent = rootView.findViewById(R.id.refresh);

        mReFreshParent.setRefreshCompleteListener(new ReFreshParent.RefreshCompleteListener() {

            @Override
            public void refreshed() {
                Toast.makeText(App.getmInstance(),"刷新完成",Toast.LENGTH_SHORT).show();

                mList.add(0, "我是下拉出来的数据");
                mCommonAdapter.setDatas(mList);
                mRecyclerView.scrollBy(0,0);
            }
        });
    }

    @Override
    public void loadData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add("这是数据 " + i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommonAdapter = new CommonAdapter(mList));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pullrefresh;
    }

}
