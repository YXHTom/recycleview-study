package com.chinamall21.mobile.study.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.view.ZoomImageView;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class ScaleImageFragment extends BaseFragment {

    private ViewPager mViewPager;
    private TextView mTextView;
    private int[] mResIcons3 = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f};
    private int[] mResIcons1 = {R.mipmap.eleme, R.mipmap.see, R.mipmap.test, R.mipmap.image2};
    private int[] mResIcons2 = {R.mipmap.width,R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4, R.mipmap.image5, R.mipmap.image6, R.mipmap.image7
    , R.mipmap.image8,R.mipmap.beauty1, R.mipmap.beauty2, R.mipmap.beauty3, R.mipmap.beauty4, R.mipmap.beauty5, R.mipmap.beauty6
            , R.mipmap.beauty7, R.mipmap.beauty8, R.mipmap.beauty9, R.mipmap.beauty10, R.mipmap.beauty11, R.mipmap.beauty12, R.mipmap.beauty13,};
    String[] images = {
            "http://cache.house.sina.com.cn/citylifehouse/citylife/de/26/20090508_7339__.jpg",
            "https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg",
            "http://img3.16fan.com/live/origin/201805/21/E421b24c08446.jpg",
            "http://img3.16fan.com/live/origin/201805/21/4D7B35fdf082e.jpg",
            "http://img3.16fan.com/live/origin/201805/21/2D02ebc5838e6.jpg",
            "http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg",
            "http://img3.16fan.com/live/origin/201805/21/A1B17c5f59b78.jpg",
            "http://img3.16fan.com/live/origin/201805/21/94699b2be3cfa.jpg",
            "http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg",
            "http://img3.16fan.com/live/origin/201805/21/264Ba4860d469.jpg",
    };

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
        mTextView.setText("1/" + mResIcons2.length);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mTextView.setText((position + 1) + "/" +  mResIcons2.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mResIcons2.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final ZoomImageView imageView = new ZoomImageView(getActivity());
//            Glide.with(getActivity().getApplicationContext()).load(images[position]).asBitmap().placeholder(R.mipmap.ic_picture).
//                    into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    imageView.setImageBitmap(resource);
//                    //这里拿到网络图片的原有宽高
//                    imageView.moveCenter(resource.getWidth(), resource.getHeight());
//                }
//            }); //方法中设置asBitmap可以设置回调类型

            imageView.setImageResource(mResIcons2[position]);
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
