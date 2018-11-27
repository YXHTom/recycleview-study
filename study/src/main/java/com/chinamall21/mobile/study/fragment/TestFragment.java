package com.chinamall21.mobile.study.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/2 11:55.
 */


public class TestFragment extends Fragment {

    private String mString;
    private ImageView mImageView;

    public static TestFragment newInstance(String str) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("string", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RecyclerView rv = view.findViewById(R.id.rv);
        mImageView = view.findViewById(R.id.image);
        //192560
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(mString + i);
        }
        rv.setAdapter(new Adapter(list));
        return view;
    }

    /**
     * 根据目标View的尺寸压缩图片返回bitmap
     * @param resources
     * @param resId
     * @param width 目标view的宽
     * @param height 目标view的高
     * @return
     */
    public static Bitmap decodeBitmapFromResource(Resources resources, int resId, int width , int height){

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(resources,resId,options);
        //获取采样率
        options.inSampleSize = calculateInSampleSize(options,width,height);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources,resId,options);
    }

    /**
     * 获取采样率
     * @param options
     * @param reqWidth 目标view的宽
     * @param reqHeight 目标view的高
     * @return 采样率
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        LogUtils.LogE("originalWidth ="+originalWidth +" originalHeight ="+originalHeight);
        int inSampleSize = 1;


        if (originalHeight > reqHeight || originalWidth > reqHeight){
            int halfHeight = originalHeight / 2;
            int halfWidth = originalWidth / 2;
            //压缩后的尺寸与所需的尺寸进行比较
            while ((halfWidth / inSampleSize) >= reqHeight && (halfHeight /inSampleSize)>=reqWidth){
                inSampleSize *= 2;
            }
        }
        LogUtils.LogE("inSampleSize = "+inSampleSize);

        return inSampleSize;

    }

    class Adapter extends RecyclerView.Adapter {
        private List<String> mList;

        public Adapter(List<String> list) {
            mList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tv, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder h = (ViewHolder) holder;
            h.setData(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }

            public void setData(int position) {
                ((TextView) itemView).setText(mList.get(position));
            }
        }
    }
}
