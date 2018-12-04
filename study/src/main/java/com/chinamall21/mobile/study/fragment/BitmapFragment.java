package com.chinamall21.mobile.study.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */

public class BitmapFragment extends BaseFragment {

    public static BitmapFragment newInstance() {
        BitmapFragment fragment = new BitmapFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        ImageView iv = rootView.findViewById(R.id.iv);
        Bitmap bitmap = decodeBitmap(R.mipmap.test, 100, 100);
        iv.setImageBitmap(bitmap);
    }

    private Bitmap decodeBitmap(int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resId, options);
        options.inSampleSize = caculateInSimpleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    private int caculateInSimpleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;
            while ((halfWidth / inSampleSize >= reqWidth)
                    && (halfHeight / inSampleSize >= reqHeight)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bitmap;
    }
}
