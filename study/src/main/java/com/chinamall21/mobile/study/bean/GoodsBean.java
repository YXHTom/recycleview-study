package com.chinamall21.mobile.study.bean;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/29 15:56.
 */


public class GoodsBean {
    private int position;
    private List<TypeBean> mTypeBeanList;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<TypeBean> getTypeBeanList() {
        return mTypeBeanList;
    }

    public void setTypeBeanList(List<TypeBean> typeBeanList) {
        mTypeBeanList = typeBeanList;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "position=" + position +
                ", mTypeBeanList=" + mTypeBeanList +
                '}';
    }
}
