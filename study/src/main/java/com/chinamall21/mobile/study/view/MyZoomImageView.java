package com.chinamall21.mobile.study.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * desc：
 * author：Created by xusong on 2018/11/29 14:32.
 */
@SuppressLint("AppCompatCustomView")
public class MyZoomImageView extends ImageView implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    private Matrix mMatrix = new Matrix();

    private float mScale;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private final float[] matrixValues = new float[9];
    private static final float MAX_SCALE = 4.5f;
    private boolean mIsScale;

    public MyZoomImageView(Context context) {
        this(context, null);
    }

    public MyZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
        setBackgroundColor(Color.BLACK);
        setOnTouchListener(this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                RectF rectF = getMatrixRectF();
                //X>0左 y>0上
                if (e2.getPointerCount() == 1 && (rectF.left <= 0 || rectF.right >= getWidth() ||
                        rectF.top <= 0 || rectF.bottom >= getHeight())) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //左滑
                    if (distanceX > 0 && rectF.right <= getWidth()) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        distanceX = rectF.right - getWidth();
                    }
                    //右滑
                    if (distanceX < 0 && rectF.left >= 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        distanceX = rectF.left;
                    }
                    //上滑
                    if (distanceY > 0 && rectF.bottom <= getHeight()) {
                        distanceY = rectF.bottom - getHeight();
                    }
                    //下滑
                    if (distanceY < 0 && rectF.top >= 0) {
                        distanceY = rectF.top;
                    }
                    if (rectF.width() < getWidth()) {
                        distanceX = 0;
                    }
                    if (rectF.height() < getHeight()) {
                        distanceY = 0;
                    }
                    mMatrix.postTranslate(-distanceX, -distanceY);
                    setImageMatrix(mMatrix);
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                new Thread(new MyRunnable()).start();
                return super.onDoubleTap(e);
            }
        });

    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            if (mIsScale) {
                mMatrix.postScale(.93f, .93f, getWidth() / 2, getHeight() / 2);

            } else {
                mMatrix.postScale(1.07f, 1.07f, getWidth() / 2, getHeight() / 2);
            }
            setImageMatrix(mMatrix);
            if ((mIsScale && getScale() >= 1.0f) || (!mIsScale && getScale() < MAX_SCALE)) {
                MyZoomImageView.this.postDelayed(this, 10);
            } else {
                if (mIsScale) {
                    moveCenter();
                }
                mIsScale = !mIsScale;

            }
        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (getDrawable() == null) {
            return true;
        }
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();

        if ((scaleFactor > 1.0f && scale < MAX_SCALE) ||
                (scaleFactor < 1.0f && scale > mScale)) {

            mMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mMatrix);

            if ((scaleFactor < 1.0f && scale <= 1.5f)) {
                moveCenter();
            }
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private float getScale() {
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        moveCenter();
    }

    private void moveCenter() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            float scale = 1.0f;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            if (intrinsicWidth > getWidth() && intrinsicHeight < getHeight()) {
                scale = intrinsicWidth * 1.0f / getWidth();
            }
            if (intrinsicHeight > getHeight() && intrinsicWidth < getWidth()) {
                scale = intrinsicHeight * 1.0f / getHeight();
            }
            if (intrinsicWidth > getWidth() && intrinsicHeight > getHeight()) {
                scale = Math.min(intrinsicWidth * 1.0f / getWidth(), intrinsicHeight * 1.0f / getHeight());
            }
            mScale = scale;
            mMatrix.setScale(scale, scale);
            mMatrix.setTranslate((getWidth() - intrinsicWidth) / 2,
                    (getHeight() - intrinsicHeight) / 2);
            setImageMatrix(mMatrix);
        }
    }

    //网络加载getDrawable为null
    public void moveCenter(int width, int height) {

        float scale = 1.0f;
        int intrinsicWidth = width;
        int intrinsicHeight = height;

        if (intrinsicWidth > getWidth() && intrinsicHeight < getHeight()) {
            scale = intrinsicWidth * 1.0f / getWidth();
        }
        if (intrinsicHeight > getHeight() && intrinsicWidth < getWidth()) {
            scale = intrinsicHeight * 1.0f / getHeight();
        }
        if (intrinsicWidth > getWidth() && intrinsicHeight > getHeight()) {
            scale = Math.min(intrinsicWidth * 1.0f / getWidth(), intrinsicHeight * 1.0f / getHeight());
        }
        mScale = scale;
        mMatrix.setScale(scale, scale);
        mMatrix.setTranslate((getWidth() - intrinsicWidth) / 2,
                (getHeight() - intrinsicHeight) / 2);
        setImageMatrix(mMatrix);

    }

    /**
     * 这个方法可以获取图片平移后相对于父布局的位置
     *
     * @return
     */
    private RectF getMatrixRectF() {
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mMatrix.mapRect(rect);
        }
        return rect;
    }

}
