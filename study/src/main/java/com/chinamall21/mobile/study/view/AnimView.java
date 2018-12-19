package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.chinamall21.mobile.study.R;

/**
 * desc：
 * author：Created by xusong on 2018/12/12 18:18.
 */


public class AnimView extends View {
    private static final int INVALIDATE_TIMES = 100; //总共执行100次

    private Paint mPaint;
    private Bitmap mBitmap;
    private int mOffsetX, mOffsetY; // 图片的中间位置
    private PathMeasure mPathMeasure;
    private float mPathLength;
    private double mStep;            //distance each step
    private float mDistance;        //distance moved
    private float[] mPos;
    private float[] mTan;

    private Matrix mMatrix;
    private Path mPath;

    public AnimView(Context context) {
        this(context, null);
    }

    public AnimView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
        initPathView();
    }

    public AnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDistance = 0;
                invalidate();
            }
        });
    }


    public void initPathView() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.eleme);

        mOffsetX = mBitmap.getWidth() / 2;
        mOffsetY = mBitmap.getHeight() / 2;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPath = new Path();
        RectF rect = new RectF(100,100,900,600);
        mPath.addRoundRect(rect,200,200, Path.Direction.CW);
        mPathMeasure = new PathMeasure(mPath, false);
        mPathLength = mPathMeasure.getLength();


        mStep = mPathLength / INVALIDATE_TIMES;
        mDistance = mPathLength;
        mPos = new float[2];
        mTan = new float[2];

        mMatrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
        if (mDistance < mPathLength) {
            mPathMeasure.getPosTan(mDistance, mPos, mTan);
            mMatrix.reset();
            mMatrix.postTranslate(-mOffsetX, -mOffsetY);
            float degrees = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI);
            mMatrix.postRotate(degrees);
            mMatrix.postTranslate(mPos[0], mPos[1]);
            canvas.drawBitmap(mBitmap, mMatrix, null);
            mDistance += mStep;
            invalidate();
        }

    }
}

