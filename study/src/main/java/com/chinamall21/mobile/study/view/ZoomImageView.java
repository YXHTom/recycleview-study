package com.chinamall21.mobile.study.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * desc：
 * author：Created by xusong on 2018/9/6 12:21.
 */


@SuppressLint("AppCompatCustomView")
public class ZoomImageView extends ImageView {

    private Matrix mMatrix = new Matrix();
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private static final float MAX_SCALE = 4.0f;
    private float MIN_SCALE = 1.0f;
    private final float[] matrixValues = new float[9];
    private boolean mIsScale;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setImageMatrix(mMatrix);
        }
    };

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
        final MyRunnable runnable = new MyRunnable();
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor();

                if ((factor > 1 && getScale() <= MAX_SCALE) || (factor < 1 && getScale() > MIN_SCALE)) {

                    mMatrix.postScale(factor, factor, getWidth() / 2, getHeight() / 2);
                    setImageMatrix(mMatrix);
                }
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                post(runnable);
                return super.onDoubleTap(e);
            }

            //👈>0 👆>0
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                RectF rectF = getMatrixRectF();

                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (distanceX > 0 && rectF.right == getWidth()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (distanceX < 0 && rectF.left == 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                //X>0左 y>0上
                if (e2.getPointerCount() == 1 && (rectF.left <= 0 || rectF.right >= getWidth() ||
                        rectF.top <= 0 || rectF.bottom >= getHeight())) {
                    //左滑
                    if (distanceX > 0 && rectF.right <= getWidth()) {
                        distanceX = rectF.right - getWidth();
                    }
                    //右滑
                    if (distanceX < 0 && rectF.left >= 0) {
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
            public boolean onDown(MotionEvent e) {
                RectF rectF = getMatrixRectF();
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onDown(e);
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleGestureDetector.onTouchEvent(event);
                mGestureDetector.onTouchEvent(event);
                return true;
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
            mHandler.sendEmptyMessage(0);
            if ((mIsScale && getScale() >= MIN_SCALE) || (!mIsScale && getScale() < MAX_SCALE)) {
                ZoomImageView.this.postDelayed(this, 10);
            } else {
                if (mIsScale) {
                    //moveCenter(0, 0);
                }
                mIsScale = !mIsScale;
            }
        }
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

    private float getScale() {
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getParent().requestDisallowInterceptTouchEvent(true);
        moveAndScale();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void moveAndScale() {
        int intrinsicWidth = 0;
        int intrinsicHeight = 0;
        if (getDrawable() != null) {
            intrinsicWidth = getDrawable().getIntrinsicWidth();
            intrinsicHeight = getDrawable().getIntrinsicHeight();

        }
        float scale = 1.0f;
        if (intrinsicWidth > getWidth() && intrinsicHeight < getHeight()) {
            scale = (getWidth() * 1.0f) / (intrinsicWidth * 1.0f);
        }
        if (intrinsicWidth < getWidth() && intrinsicHeight > getHeight()) {
            scale = (getHeight() * 1.0f) / (intrinsicHeight * 1.0f);
        }

        if (intrinsicWidth > getWidth() && intrinsicHeight > getHeight()) {
            scale = Math.min((getWidth() * 1.0f) / (intrinsicWidth * 1.0f),
                    (getHeight() * 1.0f) / (intrinsicHeight * 1.0f));
        }
        if (intrinsicWidth < getWidth() && intrinsicHeight < getHeight()) {
            scale = Math.min((getWidth() * 1.0f) / (intrinsicWidth * 1.0f),
                    (getHeight() * 1.0f) / (intrinsicHeight * 1.0f));
        }
        MIN_SCALE = scale;
        if (scale < 1) {
            mMatrix.postScale(scale, scale);
            mMatrix.postTranslate(0, getHeight() / 2 - (intrinsicHeight * scale) / 2);
        } else {
            mMatrix.postTranslate((getWidth() - intrinsicWidth) / 2,
                    (getHeight() - intrinsicHeight) / 2);
            mMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        }


        setImageMatrix(mMatrix);
    }
}
