package com.chinamall21.mobile.study;

import com.chinamall21.mobile.study.base.BaseActivity;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.fragment.StudyFragment;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 16:57.
 */


public class StudyActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return StudyFragment.newInstance();
    }

}
