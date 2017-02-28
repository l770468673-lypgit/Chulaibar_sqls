package com.tianyi.chulaibar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class ProvinceCityListBean {

    /**
     * cityId : 3
     * cityName : 石家庄市
     * zipCode : 050000
     * provinceId : 3
     * hot : false
     * show : null
     */

    private List<CityListBean> cityList;

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        private int cityId;
        private String cityName;
        private String zipCode;
        private int provinceId;
        private boolean hot;
        private Object show;

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
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
