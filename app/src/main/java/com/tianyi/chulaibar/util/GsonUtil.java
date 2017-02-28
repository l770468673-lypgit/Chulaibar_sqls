package com.tianyi.chulaibar.util;

import com.google.gson.Gson;

/**
 * Json数据解析类 Description <br>
 * CreateDate 2016-12-13 <br>
 * 
 * @author LHY <br>
 */
public class GsonUtil {

        public static   Gson getInstance(){

            Gson gson = new Gson();

            return gson;
        }


}
