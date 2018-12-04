package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/9/6 12:21.
 */


public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener{
    private Matrix mMatrix = new Matrix();
    private float mScale;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private static final float MAX_SCALE = 4.0f;
    private final float[] matrixValues = new float[9];
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;
    private boolean isCanDrag;
    private float mLastX;
    private float mLastY;
    private int lastPointerCount;
    private int mTouchSlop;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);

        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.getTouchSlop();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++)
        {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        /**
         * 每当触摸点发生变化时，重置mLasX , mLastY
         */
        if (pointerCount != lastPointerCount)
        {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }


        lastPointerCount = pointerCount;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag)
                {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag)
                {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null)
                    {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if (rectF.width() < getWidth())
                        {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        // 如果高度小雨屏幕高度，则禁止上下移动
                        if (rectF.height() < getHeight())
                        {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        LogUtils.LogE("dx ="+dx +" dy ="+dy);
                        mMatrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(mMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }

        return true;

    }

    private float getScale() {
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
    private void checkMatrixBounds()
    {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom)
        {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom)
        {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight)
        {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight)
        {
            deltaX = viewWidth - rect.right;
        }
        mMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 是否是推动行为
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }



}
