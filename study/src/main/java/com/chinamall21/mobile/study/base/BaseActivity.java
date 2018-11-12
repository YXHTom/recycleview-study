package com.chinamall21.mobile.study.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.chinamall21.mobile.study.R;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 16:39.
 */


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //避免重复添加Fragment

        BaseFragment firstFragment = getFirstFragment();
        if (null != firstFragment) {
            addFragment(firstFragment);
        } else {
            throw new NullPointerException("getFirstFragment() cannot be null");
        }

    }

    public void addFragment(BaseFragment fragment) {
        if (fragment != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager()
                    .popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            } else {
                removeFragment();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract BaseFragment getFirstFragment();

}
