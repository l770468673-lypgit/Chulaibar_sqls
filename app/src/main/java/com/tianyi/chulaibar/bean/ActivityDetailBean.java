package com.tianyi.chulaibar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class ActivityDetailBean {


    /**
     * result : {"recommendList":[{"activityId":"172","activityName":"帆船入门体验之旅 感受奥帆之都青岛魅力","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160720151516375QQ图片20160719111914.png","startTime":"2016/7/14 10:00","endTime":"2016/10/31 18:00","provinceName":"山东省","cityName":"青岛市"},{"activityId":"179","activityName":"首届Super-wet水球大战\u201c湿身\u201d跑","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160725142433633湿身跑9.jpg","startTime":"2016/8/7 15:30","endTime":"2016/8/7 18:30","provinceName":"北京市","cityName":"北京市"},{"activityId":"181","activityName":"2016 山谷民谣音乐节Valley Folk Festival","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160812111234896QQ截图20160812111121.jpg","startTime":"2016/8/20 14:00","endTime":"2016/8/21 21:30","provinceName":"北京市","cityName":"北京市"},{"activityId":"183","activityName":"echo回声嘉年华音乐节","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160812113222068QQ截图20160812111849.jpg","startTime":"2016/8/13 12:00","endTime":"2016/8/14 22:00","provinceName":"上海市","cityName":"上海市"},{"activityId":"185","activityName":"巨型滑道嘉年华SLIDE THE CITY 广州站","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160811174147397图片1.png","startTime":"2016/8/19 9:00","endTime":"2016/8/21 22:00","provinceName":"广东省","cityName":"广州市"}],"partList":[{"partId":"1","activityId":"99","userId":"1","userName":"n"},{"partId":"2","activityId":"99","userId":"1","userName":"zhangsna"}],"giftList":[{"giftId":"1","activityId":"99","giftName":"11111","giftNumber":11,"surplusNum":1,"giftRemark":"11"},{"giftId":"12","activityId":"99","giftName":"9999","giftNumber":8,"surplusNum":8,"giftRemark":"1"}],"activity":{"activityId":"99","activityName":"2016如果 田馥甄巡回演唱会PLUS 北京站","userType":null,"telphone":null,"email":null,"userName":null,"head":null,"userEmail":null,"companyName":null,"companyLogo":null,"companyPhone":null,"posterPic":"\\AdminTYSystem\\upImage\\20160513141156037.jpg","aPhone":null,"number":100,"address":"北京 海淀区 乐视体育生态中心","click":18074,"collections":4524,"participation":14,"remark":null,"startTime":"2016/5/8 19:30","endTime":"2016/7/30 22:00","signUpTime":1462707000000,"signEndTime":1469808000000,"provinceName":"北京市","cityName":"北京市"}}
     * code : 0
     * type : SUCCESS
     * info :
     */

    private ResultBean result;
    private int code;
    private String type;
    private String info;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class ResultBean {
        /**
         * recommendList : [{"activityId":"172","activityName":"帆船入门体验之旅 感受奥帆之都青岛魅力","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160720151516375QQ图片20160719111914.png","startTime":"2016/7/14 10:00","endTime":"2016/10/31 18:00","provinceName":"山东省","cityName":"青岛市"},{"activityId":"179","activityName":"首届Super-wet水球大战\u201c湿身\u201d跑","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160725142433633湿身跑9.jpg","startTime":"2016/8/7 15:30","endTime":"2016/8/7 18:30","provinceName":"北京市","cityName":"北京市"},{"activityId":"181","activityName":"2016 山谷民谣音乐节Valley Folk Festival","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160812111234896QQ截图20160812111121.jpg","startTime":"2016/8/20 14:00","endTime":"2016/8/21 21:30","provinceName":"北京市","cityName":"北京市"},{"activityId":"183","activityName":"echo回声嘉年华音乐节","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160812113222068QQ截图20160812111849.jpg","startTime":"2016/8/13 12:00","endTime":"2016/8/14 22:00","provinceName":"上海市","cityName":"上海市"},{"activityId":"185","activityName":"巨型滑道嘉年华SLIDE THE CITY 广州站","userType":null,"userName":null,"head":null,"companyName":null,"companyLogo":null,"posterPic":"\\AdminTYSystem\\upImage\\thumb_20160811174147397图片1.png","startTime":"2016/8/19 9:00","endTime":"2016/8/21 22:00","provinceName":"广东省","cityName":"广州市"}]
         * partList : [{"partId":"1","activityId":"99","userId":"1","userName":"n"},{"partId":"2","activityId":"99","userId":"1","userName":"zhangsna"}]
         * giftList : [{"giftId":"1","activityId":"99","giftName":"11111","giftNumber":11,"surplusNum":1,"giftRemark":"11"},{"giftId":"12","activityId":"99","giftName":"9999","giftNumber":8,"surplusNum":8,"giftRemark":"1"}]
         * activity : {"activityId":"99","activityName":"2016如果 田馥甄巡回演唱会PLUS 北京站","userType":null,"telphone":null,"email":null,"userName":null,"head":null,"userEmail":null,"companyName":null,"companyLogo":null,"companyPhone":null,"posterPic":"\\AdminTYSystem\\upImage\\20160513141156037.jpg","aPhone":null,"number":100,"address":"北京 海淀区 乐视体育生态中心","click":18074,"collections":4524,"participation":14,"remark":null,"startTime":"2016/5/8 19:30","endTime":"2016/7/30 22:00","signUpTime":1462707000000,"signEndTime":1469808000000,"provinceName":"北京市","cityName":"北京市"}
         */

        private ActivityBean activity;
        private List<RecommendListBean> recommendList;
        private List<PartListBean> partList;
        private List<GiftListBean> giftList;

        public ActivityBean getActivity() {
            return activity;
        }

        public void setActivity(ActivityBean activity) {
            this.activity = activity;
        }

        public List<RecommendListBean> getRecommendList() {
            return recommendList;
        }

        public void setRecommendList(List<RecommendListBean> recommendList) {
            this.recommendList = recommendList;
        }

        public List<PartListBean> getPartList() {
            return partList;
        }

        public void setPartList(List<PartListBean> partList) {
            this.partList = partList;
        }

        public List<GiftListBean> getGiftList() {
            return giftList;
        }

        public void setGiftList(List<GiftListBean> giftList) {
            this.giftList = giftList;
        }

        public static class ActivityBean {
            /**
             * activityId : 99
             * activityName : 2016如果 田馥甄巡回演唱会PLUS 北京站
             * userType : null
             * telphone : null
             * email : null
             * userName : null
             * head : null
             * userEmail : null
             * companyName : null
             * companyLogo : null
             * companyPhone : null
            * posterPic :
             * aPhone : null
             * number : 100
             * address : 北京 海淀区 乐视体育生态中心
             * click : 18074
             * collections : 4524
             * participation : 14
             * remark : null
             * startTime : 2016/5/8 19:30
             * endTime : 2016/7/30 22:00
             * signUpTime : 1462707000000
             * signEndTime : 1469808000000
             * provinceName : 北京市
             * cityName : 北京市
             */

            private String activityId;
            private String activityName;
            private Object userType;
            private Object telphone;
            private Object email;
            private Object userName;
            private Object head;
            private Object userEmail;
            private Object companyName;
            private Object companyLogo;
            private Object companyPhone;
            private String posterPic;
            private Object aPhone;
            private int number;
            private String address;
            private int click;
            private int collections;
            private int participation;
            private Object remark;
            private String startTime;
            private String endTime;
            private String signUpTime;
            private String signEndTime;
            private String provinceName;
            private String cityName;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public Object getTelphone() {
                return telphone;
            }

            public void setTelphone(Object telphone) {
                this.telphone = telphone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getUserName() {
                return userName;
            }

            public void setUserName(Object userName) {
                this.userName = userName;
            }

            public Object getHead() {
                return head;
            }

            public void setHead(Object head) {
                this.head = head;
            }

            public Object getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(Object userEmail) {
                this.userEmail = userEmail;
            }

            public Object getCompanyName() {
                return companyName;
            }

            public void setCompanyName(Object companyName) {
                this.companyName = companyName;
            }

            public Object getCompanyLogo() {
                return companyLogo;
            }

            public void setCompanyLogo(Object companyLogo) {
                this.companyLogo = companyLogo;
            }

            public Object getCompanyPhone() {
                return companyPhone;
            }

            public void setCompanyPhone(Object companyPhone) {
                this.companyPhone = companyPhone;
            }

            public String getPosterPic() {
                return posterPic;
            }

            public void setPosterPic(String posterPic) {
                this.posterPic = posterPic;
            }

            public Object getAPhone() {
                return aPhone;
            }

            public void setAPhone(Object aPhone) {
                this.aPhone = aPhone;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getClick() {
                return click;
            }

            public void setClick(int click) {
                this.click = click;
            }

            public int getCollections() {
                return collections;
            }

            public void setCollections(int collections) {
                this.collections = collections;
            }

            public int getParticipation() {
                return participation;
            }

            public void setParticipation(int participation) {
                this.participation = participation;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getSignUpTime() {
                return signUpTime;
            }

            public void setSignUpTime(String signUpTime) {
                this.signUpTime = signUpTime;
            }

            public String getSignEndTime() {
                return signEndTime;
            }

            public void setSignEndTime(String signEndTime) {
                this.signEndTime = signEndTime;
            }

            public String getProvinceName() {
                return provinceName;
            }

            public void setProvinceName(String provinceName) {
                this.provinceName = provinceName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }
        }

        public static class RecommendListBean {
            /**
             * activityId : 172
             * activityName : 帆船入门体验之旅 感受奥帆之都青岛魅力
             * userType : null
             * userName : null
             * head : null
             * companyName : null
             * companyLogo : null
             * posterPic :
             * startTime : 2016/7/14 10:00
             * endTime : 2016/10/31 18:00
             * provinceName : 山东省
             * cityName : 青岛市
             */

            private String activityId;
            private String activityName;
            private Object userType;
            private Object userName;
            private Object head;
            private Object companyName;
            private Object companyLogo;
            private String posterPic;
            private String startTime;
            private String endTime;
            private String provinceName;
            private String cityName;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public Object getUserName() {
                return userName;
            }

            public void setUserName(Object userName) {
                this.userName = userName;
            }

            public Object getHead() {
                return head;
            }

            public void setHead(Object head) {
                this.head = head;
            }

            public Object getCompanyName() {
                return companyName;
            }

            public void setCompanyName(Object companyName) {
                this.companyName = companyName;
            }

            public Object getCompanyLogo() {
                return companyLogo;
            }

            public void setCompanyLogo(Object companyLogo) {
                this.companyLogo = companyLogo;
            }

            public String getPosterPic() {
                return posterPic;
            }

            public void setPosterPic(String posterPic) {
                this.posterPic = posterPic;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getProvinceName() {
                return provinceName;
            }

            public void setProvinceName(String provinceName) {
                this.provinceName = provinceName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }
        }

        public static class PartListBean {
            /**
             * partId : 1
             * activityId : 99
             * userId : 1
             * userName : n
             */

            private String partId;
            private String activityId;
            private String userId;
            private String userName;

            public String getPartId() {
                return partId;
            }

            public void setPartId(String partId) {
                this.partId = partId;
            }

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class GiftListBean {
            /**
             * giftId : 1
             * activityId : 99
             * giftName : 11111
             * giftNumber : 11.0
             * surplusNum : 1.0
             * giftRemark : 11
             */

            private String giftId;
            private String activityId;
            private String giftName;
            private double giftNumber;
            private double surplusNum;
            private String giftRemark;

            public String getGiftId() {
                return giftId;
            }

            public void setGiftId(String giftId) {
                this.giftId = giftId;
            }

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getGiftName() {
                return giftName;
            }

            public void setGiftName(String giftName) {
                this.giftName = giftName;
            }

            public double getGiftNumber() {
                return giftNumber;
            }

            public void setGiftNumber(double giftNumber) {
                this.giftNumber = giftNumber;
            }

            public double getSurplusNum() {
                return surplusNum;
            }

            public void setSurplusNum(double surplusNum) {
                this.surplusNum = surplusNum;
            }

            public String getGiftRemark() {
                return giftRemark;
            }

            public void setGiftRemark(String giftRemark) {
                this.giftRemark = giftRemark;
            }
        }
    }
}
