package com.chinamall21.mobile.study.fragment;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class ImageFragment extends BaseFragment {

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        Switch sView = rootView.findViewById(R.id.sw);
        sView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.LogE("isChecked ="+isChecked);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_circleimage;
    }
}

