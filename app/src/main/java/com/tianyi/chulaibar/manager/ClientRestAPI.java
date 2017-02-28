package com.tianyi.chulaibar.manager;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Define all rest API with server here Use open source Retrofit for http access
 * http://square.github.io/retrofit/
 *
 * @author liuyanpeng07
 */
public interface ClientRestAPI {

    //activity/addActivity.action

    //http://api.chulaibar.com/CLBarPro/app/activity/addActivity.action
    @FormUrlEncoded
    @POST("activity/addActivity.action")
    Call<ResponseHead> getaddActivity(
            @Field("activityId") String activityId,
            @Field("activityName") String activityName,
            @Field("userId") String userId,
            @Field("activityType") int activityType,
            // @Field("labelIds") String labelIds,
            @Field("posterPic") String posterPic,
            @Field("provinceId") int provinceId,
            @Field("cityId") int cityId,
            //   @Field("districtId") int districtId,
            @Field("address") String address,
            //   @Field("startDate") String startDate,
            @Field("startTime") String startTime,
            @Field("endTime") String endTime,
            @Field("week") String week,
            //   @Field("endDate") String endDate,

            @Field("aPhone") String aPhone,
            @Field("number") int number,
            @Field("remark") String remark,
            //    @Field("createTime") String createTime,
            @Field("signUpTime") String signUpTime,
            @Field("signEndTime") String signEndTime,
            @Field("privacy") boolean privacy
            //   @Field("intrust") boolean intrust,
            //  @Field("recommend") boolean recommend


            //  @GET("house/getMyHouseOwners")
            //  Call<Bean_FamilyList> getFramilyLists(@Query("token") String token);


            //activity/getNewActivityId.action


    );

}









