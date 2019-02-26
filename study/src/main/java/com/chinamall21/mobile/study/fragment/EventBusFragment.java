package com.chinamall21.mobile.study.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.bean.TypeBean;
import com.chinamall21.mobile.study.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * desc：
 * author：Created by xusong on 2019/1/15 17:04.
 */


public class EventBusFragment extends BaseFragment implements View.OnClickListener {

    public static EventBusFragment newInstance(){
        return new EventBusFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.bt_post).setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_eventbus;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_post:

                break;
        }
    }

    @Subscribe()
    public void onMessageMent(TypeBean typeBean){
        LogUtils.LogE(typeBean.toString());
    }
}
