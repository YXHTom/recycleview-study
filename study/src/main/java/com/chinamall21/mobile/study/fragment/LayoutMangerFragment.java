package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;

/**
 * desc：
 * author：Created by xusong on 2018/11/14 14:57.
 */

public class LayoutMangerFragment extends BaseFragment implements View.OnClickListener {

    public static LayoutMangerFragment newInstance() {
        LayoutMangerFragment fragment = new LayoutMangerFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
         rootView.findViewById(R.id.bt1).setOnClickListener(this);
         rootView.findViewById(R.id.bt2).setOnClickListener(this);
         rootView.findViewById(R.id.bt3).setOnClickListener(this);
         rootView.findViewById(R.id.bt4).setOnClickListener(this);
         rootView.findViewById(R.id.bt5).setOnClickListener(this);
         rootView.findViewById(R.id.bt6).setOnClickListener(this);
         rootView.findViewById(R.id.bt7).setOnClickListener(this);
         rootView.findViewById(R.id.bt8).setOnClickListener(this);
         rootView.findViewById(R.id.bt9).setOnClickListener(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_layoutmanager;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                addFragment(MyLayoutMangerFragment.newInstance(1));

                break;
            case R.id.bt2:
                addFragment(MyLayoutMangerFragment.newInstance(2));
                break;
            case R.id.bt3:
                addFragment(MyLayoutMangerFragment.newInstance(3));
                break;
            case R.id.bt4:
                addFragment(MyLayoutMangerFragment.newInstance(4));
                break;
            case R.id.bt5:
                addFragment(MyLayoutMangerFragment.newInstance(5));
                break;
            case R.id.bt6:
                addFragment(MyLayoutMangerFragment.newInstance(6));
                break;
            case R.id.bt7:
                addFragment(MyLayoutMangerFragment.newInstance(7));
                break;
            case R.id.bt8:
                addFragment(MyLayoutMangerFragment.newInstance(8));
                break;
            case R.id.bt9:
                addFragment(MyLayoutMangerFragment.newInstance(9));
                break;
        }

    }
}
