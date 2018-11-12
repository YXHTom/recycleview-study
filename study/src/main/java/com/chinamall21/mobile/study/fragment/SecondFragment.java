package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 16:56.
 */


public class SecondFragment extends BaseFragment {

    public static BaseFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }



    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.LogE("Bt2");
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_two;
    }

}
