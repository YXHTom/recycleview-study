package com.chinamall21.mobile.study.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.okhttp.ProgressInterceptor;
import com.chinamall21.mobile.study.okhttp.ProgressListener;
import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class GlideFragment extends BaseFragment {
    private String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    private String GIF = "https://upload-images.jianshu.io/upload_images/5872156-029c6eaa2a3c47ac.gif?imageMogr2/auto-orient/strip";
    private String URL = "https://www.baidu.com/img/bd_logo1.png";
    private String URL2 = "http://cn.bing.com/az/hprichbg/rb/AvalancheCreek_ROW11173354624_1920x1080.jpg";
    private String URL3 = "http://guolin.tech/book.png";
    private String URL4 = "http://guolin.tech/test.gif";

    public static GlideFragment newInstance() {
        GlideFragment fragment = new GlideFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Button button = rootView.findViewById(R.id.load);
        final ImageView imageView = rootView.findViewById(R.id.image);

        final SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            }
        };
        ProgressInterceptor.addListener(GIF, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                LogUtils.LogE("progress =" + progress);
                progressDialog.setProgress(progress);
                if (progress >= 100)
                    progressDialog.dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Glide.with(GlideFragment.this).load(GIF).
                        diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_glide;
    }
}
