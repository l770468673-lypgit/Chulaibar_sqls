package com.tianyi.chulaibar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class HotCityBean {


    private List<CityListBean> cityList;

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        /**
         * cityId : 1
         * cityName : 北京市
         */

        private int cityId;
        private String cityName;

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
    }
}
