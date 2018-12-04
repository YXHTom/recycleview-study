package com.chinamall21.mobile.study.fragment;

import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 15:05.
 */


public class LayoutFragment extends BaseFragment implements View.OnClickListener {

    public static LayoutFragment newInstance() {
        LayoutFragment fragment = new LayoutFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        Button bt1 = rootView.findViewById(R.id.bt1);
        Button bt2 = rootView.findViewById(R.id.bt2);
        TextView tv = rootView.findViewById(R.id.tv);
        ViewStub viewStub = rootView.findViewById(R.id.viewstub);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        tv.setOnClickListener(this);
        viewStub.setLayoutResource(R.layout.rv_header);
        viewStub.inflate();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                LogUtils.LogE("bt1");
                break;
            case R.id.bt2:
                LogUtils.LogE("bt2");
                break;
            case R.id.tv:
                LogUtils.LogE("tv");
                break;
        }
    }
}
