package com.chinamall21.mobile.study.bean;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/18 10:24.
 */


public class DataBean {

    private String name;
    private List<DatasBean> citys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DatasBean> getCitys() {
        return citys;
    }

    public void setCitys(List<DatasBean> citys) {
        this.citys = citys;
    }

    public static class DatasBean {
        /**
         * label : 北京Beijing010
         * name : 北京
         * pinyin : Beijing
         * zip : 010
         */

        private String label;
        private String name;
        private String pinyin;
        private String zip;
        private String img;
        private boolean isTitle;


        public boolean isTitle() {
            return isTitle;
        }

        public void setTitle(boolean title) {
            isTitle = title;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "label='" + label + '\'' +
                    ", name='" + name + '\'' +
                    ", pinyin='" + pinyin + '\'' +
                    ", zip='" + zip + '\'' +
                    ", isTitle=" + isTitle +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "name='" + name + '\'' +
                ", citys=" + citys +
                '}';
    }
}
