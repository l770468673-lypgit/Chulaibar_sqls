package com.tianyi.chulaibar.manager;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Use open source Retrofit for http access http://square.github.io/retrofit/
 * 
 * @author lizhiqiang3
 * 
 */
public class HttpManager {
    private final static String TAG = "HttpManager";


    private final static String CONNECT_LOGIN = "http://api.chulaibar.com/CLBarPro/app/";


    private final static String SERVER_REST_API_URL = CONNECT_LOGIN;

    private static HttpManager sHttpManager;

    public static HttpManager getInstance() {
        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager();
                    sHttpManager.init();
                }
            }
        }
        return sHttpManager;
    }


    private ClientRestAPI mHttpClient;
    private Retrofit mRetrofit;

    private void init() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(CONNECT_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mHttpClient = mRetrofit.create(ClientRestAPI.class);

    }

    public ClientRestAPI getHttpClient() {
        return mHttpClient;
    }
}
