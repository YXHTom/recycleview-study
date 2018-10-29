package com.chinamall21.mobile.study.eventBus;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/10 16:03.
 */


public class ActivityUtils {

    private List<Activity> mActivities = new ArrayList<>();

    public void addActivity(Activity activity){
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity){
        mActivities.remove(activity);
    }

    public void finishAll(){
        for(Activity activity : mActivities){
            activity.finish();
        }
    }
}
