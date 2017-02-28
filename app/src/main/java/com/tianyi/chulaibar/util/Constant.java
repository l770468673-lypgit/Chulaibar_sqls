package com.tianyi.chulaibar.util;

/**
 * Created by Ken on 2016/3/7.
 * 常量接口类
 */
public interface Constant {


    interface USERTYPE {
        int user_geren = 1;//用户类型，个人
        int user_qiye = 2;//用户类型，企业
    }


    interface CODE {
        int REUQEST_CODE = 0x001;//请求码.
        int RESULT_CODE = 0x002;//结果码.
        int REUQEST_CODE1 = 0x003;//请求码.
        int RESULT_CODE1 = 0x004;//结果码.
        int REUQEST_CODE2 = 0x005;//请求码.
        int RESULT_CODE2 = 0x006;//结果码.
        int BIAOSHI_CODE = 0x004;//标实码.
        int RESULT_CODE3 = 0x007;//结果码.
        int XIANGCEBIAOSHI_CODE = 0x005;//相册标实码.


        int TAKE_PHOTO_XIANGJI = 1;
        int CROP_PHOTO_ONE = 2;
        int TAKE_CAMEAR_XIANGCE = 3;
        int SET_CAMEAR_VIEW = 4;


        int TAKE_PHOTO_XIANGJI_HAIBAO = 1;
        int CROP_PHOTO_ONE_HAIBAO = 2;
        int TAKE_CAMEAR_XIANGCE_HAIBAO = 3;
        int SET_CAMEAR_VIEW_HAIBAO = 4;


    }

    interface Shares {
     }

    interface URL {


        String Base_URL = "http://api.chulaibar.com/CLBarPro/app/";//服务器的


        String GETNEWACTIVITYID = "http://api.chulaibar.com/CLBarPro/app/activity/getNewActivityId.action";


        String GERENZHUCE_URL = Base_URL + "users/regUsers1.action" + "?telphone=%s&password=%s&nickname=%s";//1.个人注册
        String QIYEZHUCE_URL = Base_URL + "users/regUsers2.action" + "?email=%s&password=%s";//2.企业注册
        String LOGIN_URL = Base_URL + "users/login.action" + "?user=%s&password=%s&userType=%s";//3.用户登录接口
        String QUERYUSERS_URL = Base_URL + "users/queryUsers.action";//4.查询用户信息接口
        String CHANGEPASSWORD_URL = Base_URL + "users/changePassword.action" + "?userId=%s&password=%s&passwordNew=%s";//5.修改个人用户密码接口
        String CHANGEPHONE_URL = Base_URL + "users/changeTelphone.action" + "?userId=%s&telphone=%s";//6.修改注册手机号码接口
        String CHANGEEMAIL_URL = Base_URL + "users/changeEmail.action";//7.修改注册邮箱接口
        String QUERYUSERDARA_URL = Base_URL + "users/queryUserData.action";//8.查询个人用户资料信息接口
        String EDITUSERDATA_URL = Base_URL + "users/editUserData.action";//9.提交个人用户资料信息接口
        String QUERYCOMPANYDATA_URL = Base_URL + "users/queryCompanyData.action";//10.查询企业用户资料信息接口
        String EDITCOMPANYDATA_URL = Base_URL + "users/editCompanyData.action";//11.提交企业用户资料信息接口
        String EDITLEGALIZE_URL = Base_URL + "users/editLegalize.action";//12.提交用户认证资料信息接口
        String ADDATTENTIONSPONSOR_URL = Base_URL + "collection/addAttentionSponsor.action";//13.添加用户关注主办方接口
        String DELATTENTIONSPONSOR_URL = Base_URL + "collection/delAttentionSponsor.action";//14.删除用户关注主办方接口
        String QUERYATTENTIONSPONSORLIST_URL = Base_URL + "collection/queryAttentionSponsorList.action";//15.查询用户关注主办方信息列表接口
        String ADDATTENTIONUSER_URL = Base_URL + "collection/addAttentionUser.action";//16.关注用户接口
        String DELATTENTIONUSER_URL = Base_URL + "collection/delAttentionUser.action";//17.取消关注用户接口
        String QUERYATTENTIONNUM_URL = Base_URL + "collection/queryAttentionNum.action";//18.查询用户关注数量接口
        String QUERYATTENTIONLIST_URL = Base_URL + "collection/queryAttentionList.action";//19.查询用户关注列表接口
        String QUERYFANSNUM_URL = Base_URL + "collection/queryFansNum.action";//20.查询关注用户的粉丝数量接口
        String QUERYFANSLIST_URL = Base_URL + "collection/queryFansList.action";//21.查询关注用户的粉丝列表接口
        String ADDCOLLECTIONS_URL = Base_URL + "collection/addCollections.action";//22.用户收藏活动接口
        String DELCOLLECTIONS_URL = Base_URL + "collection/delCollections.action";//23.用户删除收藏活动接口
        String QUERYCOLLECTIONSLIST_URL = Base_URL + "collection/queryCollectionsList.action";//24.查询用户收藏的活动信息列表接口
        // String QUERYACTIVITYLIST_URL=Base_URL+"activity/queryActivityList.action"+"?keywords=%s&cityId=%d&beginDate=%s&typeId=%d&fee=%s&currPage=%s&pageSize=%s";//25.发现界面搜索活动
        String QUERYACTIVITYLIST_URL = Base_URL + "activity/queryActivityList.action" + "?keywords=%s";//25.发现界面关键字搜索活动
        String QUERYACTIVITYLIST_FRESH_URL = Base_URL + "activity/queryActivityList.action";//首页最新活动列表
        String QUERYACTIVITYLIST_HOT_URL = Base_URL + "activity/queryActivityList.action" + "?hot=%s";//首页最热活动列表

        String SENDMESSAGE_URL = Base_URL + "other/sendMsg.action" + "?phones=%s";//26.个人注册时，获取短信验证码
        String QUERYBYTELUSERS_URL = Base_URL + "users/queryByTelUsers.action" + "?telphone=%s";//27.根据手机号查询用户信息
        String MODIFYPASSWORD_URL = Base_URL + "users/modifyPassword.action" + "?userId=%s&password=%s"; //28.忘记密码接口

        String ADDACTIVITYTYPE_URL = Base_URL + "at/addActivityType.action" + "?typeName=%s&description=%s&sort=%d";//29.发布界面添加活动类型时获取类型的int值
        String QUERYACTIVITYTYPELIST_URL = Base_URL + "at/queryActivityTypeList.action" + "?statusName=%s";//30.发布和发现界面，获取活动类型列表
        String QUERYYCITYHOT_URL = Base_URL + "city/queryCityHot.action" + "?queryTop=%d";//31.发现界面，获取热门城市

        String DISCOVERPAGER_SEARCH_URL = Base_URL + "activity/queryActivityList.action?keywords=%s";//发现界面的搜索URL
        String PROVINCE_LIST_URL = "http://api.chulaibar.com/CLBarPro/app/city/queryProvinceSe.action?provinceId=0";//省级列表的数据
        String CIITY_LIST_URL = "http://api.chulaibar.com/CLBarPro/app/city/queryCitySe.action?provinceId=%d&cityId=0";//省级列表的数据

        String UPLOADIMAGE_URL = Base_URL + "common/uploadImage.action";//34.发布界面：上传图片，单图片上传，多图片上传
        String UPLOADVIDEO_URL = Base_URL + "common/uploadVideo.action";//35.发布界面：上传视频

        String IMAGE_HEAD_URL = "http://115.28.88.127:88/";//36.图片网址前半部分


        //    String GETDATAS="http://restapi.amap.com/v3/geocode/geo?key=1deafabbfcb63a076a9073c9c44fdb8c&s=rsv3&city=35&address=%s";//自己项目的key
        String GETDATAS = "http://restapi.amap.com/v3/geocode/geo?key=389880a06e3f893ea46036f030c94700&s=rsv3&city=35&address=%s";//?这里的key不是自己项目的key，以后出问题再换成自己的

        String QUERYACTIVITYINFO_URL = Base_URL + "activity/queryActivityInfo.action" + "?activityId=%s";//首页获取活动详细信息以及相关信息


        String ABOUTUS_URL = "https://api.chulaibar.com/CLBarPro/us.html";//首页获取活动详细信息以及相关信息

        String TIJIAOTUPIANURL = " http://api.chulaibar.com/CLBarPro/app/common/uploadImage.action"; // t图片

        String HUOQUMOBANTUPIAN = "http://api.chulaibar.com/CLBarPro/app/other/queryAttachmentMBList.action";

    }


}
