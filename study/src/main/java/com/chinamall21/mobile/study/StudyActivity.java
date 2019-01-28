package com.chinamall21.mobile.study;

import com.chinamall21.mobile.study.base.BaseActivity;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.bean.TypeBean;
import com.chinamall21.mobile.study.fragment.StudyFragment;
import com.chinamall21.mobile.study.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 16:57.
 */


public class StudyActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        EventBus.getDefault().post(new TypeBean(23,"mj"));
        return StudyFragment.newInstance();
    }

    @Subscribe
    public void onMessageEvent(TypeBean typeBean){
        LogUtils.LogE(typeBean.toString());
    }

}
