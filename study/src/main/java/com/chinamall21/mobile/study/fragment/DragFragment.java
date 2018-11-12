package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class DragFragment extends BaseFragment {

    public static DragFragment newInstance(){
        DragFragment fragment = new DragFragment();
        return fragment;
    }


    @Override
    protected void initView(View rootView) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_drag;
    }
}
