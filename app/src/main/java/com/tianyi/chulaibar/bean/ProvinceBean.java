package com.tianyi.chulaibar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class ProvinceBean {

    /**
     * provinceId : 1
     * provinceName : 北京市
     * enId : CN.BJ
     * hot : false
     * show : null
     */

    private List<ProvinceListBean> provinceList;

    public List<ProvinceListBean> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<ProvinceListBean> provinceList) {
        this.provinceList = provinceList;
    }

    public static class ProvinceListBean {
        private int provinceId;
        private String provinceName;
        private String enId;
        private boolean hot;
        private Object show;

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getEnId() {
            return enId;
        }

        public void setEnId(String enId) {
            this.enId = enId;
        }

        public boolean isHot() {
            return hot;
        }

        public void setHot(boolean hot) {
            this.hot = hot;
        }

        public Object getShow() {
            return show;
        }

        public void setShow(Object show) {
            this.show = show;
        }
    }
}
