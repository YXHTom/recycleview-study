package com.chinamall21.mobile.study.fragment;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.Images;
import com.chinamall21.mobile.study.view.MyZoomImageView;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class ScaleImageFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TextView mTextView;
    private int[] mResIcons = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f};

    public static ScaleImageFragment newInstance() {
        ScaleImageFragment fragment = new ScaleImageFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.viewpager);
        mTextView = rootView.findViewById(R.id.tv);
        //加载本地资源图片
        //mTextView.setText("1/" + mResIcons.length);
        //加载网络资源图片
        mTextView.setText("1/" + Images.imageUrls.length);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mTextView.setText((position + 1) + "/" +  Images.imageUrls.length);

            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Images.imageUrls.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final MyZoomImageView imageView = new MyZoomImageView(getActivity());
            Glide.with(getActivity().getApplicationContext()).load(Images.imageUrls[position]).asBitmap().placeholder(R.mipmap.ic_picture).
                    into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImageBitmap(resource);
                    //这里拿到网络图片的原有宽高
                    imageView.moveCenter(resource.getWidth(), resource.getHeight());
                }


            }); //方法中设置asBitmap可以设置回调类型

            //imageView.setImageResource(mResIcons[position]); 加载本地
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bitmap;
    }


}
