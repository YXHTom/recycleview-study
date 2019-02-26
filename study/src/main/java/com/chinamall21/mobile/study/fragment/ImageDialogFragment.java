package com.chinamall21.mobile.study.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.Images;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class ImageDialogFragment extends BaseFragment {

    public static ImageDialogFragment newInstance(){
        ImageDialogFragment fragment = new ImageDialogFragment();
        return fragment;
    }


    @Override
    protected void initView(View rootView) {
        final ImageView iv1 = rootView.findViewById(R.id.iv1);
        final ImageView iv2 = rootView.findViewById(R.id.iv2);

        int random = new Random().nextInt(Images.imageUrls.length);

        Glide.with(getActivity()).load(Images.imageUrls[random]).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv1.setImageBitmap(resource);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resource.compress(Bitmap.CompressFormat.JPEG,20,baos);
                byte[] bytes = baos.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                LogUtils.LogE(resource.getByteCount()+"..."+bytes.length);
                iv2.setImageBitmap(bitmap);
            }
        });

    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialog_fragment;
    }
}
