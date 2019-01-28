package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:16.
 */


public class StudyFragment extends BaseFragment implements View.OnClickListener {

    public static StudyFragment newInstance(){
        StudyFragment fragment = new StudyFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.bt1).setOnClickListener(this);
        rootView.findViewById(R.id.bt2).setOnClickListener(this);
        rootView.findViewById(R.id.bt3).setOnClickListener(this);
        rootView.findViewById(R.id.bt4).setOnClickListener(this);
        rootView.findViewById(R.id.bt5).setOnClickListener(this);
        rootView.findViewById(R.id.bt7).setOnClickListener(this);
        rootView.findViewById(R.id.bt8).setOnClickListener(this);
        rootView.findViewById(R.id.bt9).setOnClickListener(this);
        rootView.findViewById(R.id.bt10).setOnClickListener(this);
        rootView.findViewById(R.id.bt12).setOnClickListener(this);
        rootView.findViewById(R.id.bt13).setOnClickListener(this);
        rootView.findViewById(R.id.bt14).setOnClickListener(this);
        rootView.findViewById(R.id.bt15).setOnClickListener(this);
        rootView.findViewById(R.id.bt16).setOnClickListener(this);
        rootView.findViewById(R.id.bt17).setOnClickListener(this);
        rootView.findViewById(R.id.bt18).setOnClickListener(this);
        rootView.findViewById(R.id.bt19).setOnClickListener(this);
        rootView.findViewById(R.id.bt20).setOnClickListener(this);
        rootView.findViewById(R.id.bt21).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                addFragment(MainFragment.newInstance());
                break;
            case R.id.bt2:
                addFragment(DragFragment.newInstance());
                break;
            case R.id.bt3:
                addFragment(DrawerFragment.newInstance());
                break;
            case R.id.bt4:
                addFragment(PullRefreshFragment.newInstance());
                break;
            case R.id.bt5:
                addFragment(LayoutMangerFragment.newInstance());
                break;
            case R.id.bt7:
                addFragment(ImageFragment.newInstance());
                break;
            case R.id.bt8:
                addFragment(RetrofitFragment.newInstance());
                break;
            case R.id.bt9:
                addFragment(PathFragment.newInstance());
                break;
            case R.id.bt10:
                addFragment(LayoutFragment.newInstance());
                break;
            case R.id.bt12:
                addFragment(DrawableFragment.newInstance());
                break;
            case R.id.bt13:
                addFragment(ScaleImageFragment.newInstance());
                break;
            case R.id.bt14:
                addFragment(OKHttpFragment.newInstance());
                break;
            case R.id.bt15:
                addFragment(OKHttpFragment.newInstance());
                break;
            case R.id.bt16:
                addFragment(ViewFragment.newInstance());
                break;
            case R.id.bt17:
                addFragment(GlideFragment.newInstance());
                break;
            case R.id.bt18:
                addFragment(NumberProgressFragment.newInstance());
                break;
            case R.id.bt19:
                addFragment(ConstraintLayoutFragment.newInstance());
                break;
            case R.id.bt20:
                addFragment(ImageDialogFragment.newInstance());
                break;
            case R.id.bt21:
                addFragment(EventBusFragment.newInstance());
                break;
        }
    }

}
