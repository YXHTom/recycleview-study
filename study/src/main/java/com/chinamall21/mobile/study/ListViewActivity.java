package com.chinamall21.mobile.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.chinamall21.mobile.study.bitmap.MyBitmapUtils1;
import com.chinamall21.mobile.study.utils.Images;

import java.util.Arrays;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/7 15:43.
 */


public class ListViewActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mImgList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Intent intent = getIntent();
        mListView = findViewById(R.id.lv);
        mImgList = Arrays.asList(Images.imageUrls);
        mListView.setAdapter(new ImageAdapter());
    }

    private class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_iv, null);
                viewHolder = new ViewHolder();
                //viewHolder.imageView = convertView.findViewById(R.id.item_iv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imageView.setImageResource(R.drawable.ic_launcher_background);
            viewHolder.imageView.setTag(mImgList.get(position));

            MyBitmapUtils1 myBitmapUtils = new MyBitmapUtils1();
            myBitmapUtils.loadImage(viewHolder.imageView, mImgList.get(position));
            //Glide.with(getApplicationContext()).load(mImgList.get(position)).into(viewHolder.imageView);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
        }
    }

}
