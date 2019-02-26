package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewConfiguration;

import com.chinamall21.mobile.study.layoutmanager.GalleryManager1;

/**
 * desc：
 * author：Created by xusong on 2019/1/27 15:46.
 */


public class GalleryRecycleview extends RecyclerView {
    public GalleryRecycleview(Context context) {
        this(context,null);
    }

    public GalleryRecycleview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int center = getlayoutManager().getCenterPos()
                - getlayoutManager().getFistvisiblePos(); //计算正在显示的所有Item的中间位置
        int order;

        if (i == center) {
            order = childCount - 1;
        } else if (i > center) {
            order = center + childCount - 1 - i;
        } else {
            order = i;
        }
        return order;

    }

    private GalleryManager1 getlayoutManager(){
        return (GalleryManager1) getLayoutManager();
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        //缩小滚动距离
        int flingX = (int) (velocityX * 0.40f);
        GalleryManager1 manger = getlayoutManager();
        double distance = getSplineFlingDistance(flingX);
        double newDistance = manger.calculateDistance(velocityX,distance);
        int fixVelocityX = getVelocity(newDistance);
        if (velocityX > 0) {
            flingX = fixVelocityX;
        } else {
            flingX = -fixVelocityX;
        }
        return super.fling(flingX, velocityY);
    }


    /**
     * 根据松手后的滑动速度计算出fling的距离
     *
     * @param velocity
     * @return
     */
    private double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * getPhysicalCoeff() * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    /**
     * 根据距离计算出速度
     *
     * @param distance
     * @return
     */
    private int getVelocity(double distance) {
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        double aecel = Math.log(distance / (mFlingFriction * mPhysicalCoeff)) * decelMinusOne / DECELERATION_RATE;
        return Math.abs((int) (Math.exp(aecel) * (mFlingFriction * mPhysicalCoeff) / INFLEXION));
    }

    /**
     * --------------flling辅助类---------------
     */
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private float mPhysicalCoeff = 0;

    private double getSplineDeceleration(int velocity) {
        final float ppi = this.getResources().getDisplayMetrics().density * 160.0f;
        float mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi
                * 0.84f; // look and feel tuning


        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    private float getPhysicalCoeff() {
        if (mPhysicalCoeff == 0) {
            final float ppi = this.getResources().getDisplayMetrics().density * 160.0f;
            mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                    * 39.37f // inch/meter
                    * ppi
                    * 0.84f; // look and feel tuning
        }
        return mPhysicalCoeff;
    }

}
