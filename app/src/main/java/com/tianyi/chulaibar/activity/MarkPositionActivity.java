package com.tianyi.chulaibar.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.adapter.MarkCityAdapter;
import com.tianyi.chulaibar.bean.Beannloaction;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.OkHttpUtil;
import com.tianyi.chulaibar.util.ToastUtil;
import com.tianyi.chulaibar.util.Utils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkPositionActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener,
        AMap.OnCameraChangeListener, TextWatcher, Inputtips.InputtipsListener {
    String city;
    MapView mMapView = null;
    private AMap aMap;
    private AMapLocationClient locationClient = null;
    private LatLonPoint lp;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private ListView address;
    private PoiSearch poiSearch;
    private myPoiOverlay poiOverlay;// poi图层
    private List<PoiItem> poiItems;// poi数据
    private List<Map<String, String>> address_list;
    private TextView location, success;
    private String keyWord = "";
    private AutoCompleteTextView mSearchText;
    private ListView minputlist;
    private int flag = 1, itemflag = 1;
    private List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_position);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);

        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_edittext);
        mSearchText.addTextChangedListener(this);
        aMap = mMapView.getMap();
        address = (ListView) findViewById(R.id.address);
        location = (TextView) findViewById(R.id.location);
        success = (TextView) findViewById(R.id.success);
        minputlist = (ListView) findViewById(R.id.inputlist);

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(MarkPositionActivity.this, MainActivity.class);
                intnt.putExtra("mapaddress", location.getText());
                setResult(Constant.CODE.RESULT_CODE3, intnt);
                finish();
            }
        });


        //addMarket(aMap);
        initLocation();
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        locationClient.setLocationOption(locationOption);
        aMap.setOnCameraChangeListener(this);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        return mOption;
    }

    /**
     * 定位监听
     */
    public AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                String result = Utils.getLocationStr(loc);
                double longitude = loc.getLongitude();
                double latitude = loc.getLatitude();
                lp = new LatLonPoint(longitude, latitude);//设置经纬度
                location.setText(loc.getAddress());
                city = loc.getCity().toString().substring(0, 3);
                Log.d("tag", "city is " + city);
                locationClient.stopLocation();
                initLocationPoi(lp);
                Log.e("result", result);
                // 停止定位

            } else {
                Log.e("result", "定位失败，loc is null");
            }
        }
    };


    private void initLocationPoi(LatLonPoint lp0) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lp0.getLatitude(), lp0.getLongitude()), 14));
        doSearchQuery(lp0);
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(LatLonPoint lp0) {
//        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                new LatLng(lp0.getLatitude(),lp0.getLongitude()), 18, 30, 30)));
        keyWord = mSearchText.getText().toString().trim();
        currentPage = 0;
        //    query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        // TODO: 2016/12/25  
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {   //d
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp0, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        searchIng(result, rcode);
    }

    private void searchIng(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();
                        poiOverlay = new myPoiOverlay(aMap, poiItems);
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
//                        MarkCityAdapter adapter = new MarkCityAdapter(MarkPositionActivity.this,address_list);
//                        address.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();

                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        //ToastUtil.show(MarkPositionActivity.this,
                        // R.string.no_results);
                    }
                }
            } else {
                //ToastUti.show(MarkPositionActivity.this, R.string.no_result);
            }
        } else {
            //ToastUtil.showerror(this.getApplicationContext(), rcode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.show(this, infomation);

    }


    @Override
    public void onCameraChange(CameraPosition position) {
// TODO: 2016/12/25
//        flag=1;
        minputlist.setVisibility(View.GONE);
        updateLocation(position);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition position) {
//        flag=1;
        updateLocation(position);

    }

    private void updateLocation(CameraPosition position) {


        LatLng target = position.target;
        double latitude = target.latitude;
        double longitude = target.longitude;
        LatLonPoint lp2 = new LatLonPoint(longitude, latitude);
        Log.e("target", "latitude:" + latitude + "longitude:" + longitude);
        doSearchQuery(lp2);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (itemflag == 1) {
            minputlist.setVisibility(View.VISIBLE);
        } else if (itemflag == 2) {
            minputlist.setVisibility(View.GONE);
        }

        itemflag = 1;

        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, city);
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(MarkPositionActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            listString.clear();
            for (int i = 0; i < tipList.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                listString.add(map);
            }
            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_layout,
                    new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});

            minputlist.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
            minputlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    itemflag = 2;
                    minputlist.setVisibility(View.GONE);
                    mSearchText.setText(listString.get(position).get("name"));

                    String url = String.format(Constant.URL.GETDATAS, listString.get(position).get("name"));
                    OkHttpUtil.downJSON(url, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) throws JSONException {
                            if (json != null) {
                                final Beannloaction beannloaction = new Gson().fromJson(json, Beannloaction.class);
                                final List<Beannloaction.GeocodesBean> geocodes =
                                        beannloaction.getGeocodes();

                                if (geocodes.size() == 0) {
                                    return;
                                }

                                final String location = geocodes.get(0).getLocation();

                                Double lng = Double.valueOf(location.substring(0, location.indexOf(",")));
                                Double lat = Double.valueOf(location.substring(location.indexOf(",") + 1));

                                doSearchQuery(new LatLonPoint(lng, lat));
                            }
                        }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });


                    //keyWord = listString.get(position).get("name");
                }
            });

        } else {
            ToastUtil.showerror(this.getApplicationContext(), rCode);
        }
    }

    /**
     * 自定义PoiOverlay
     */
    private class myPoiOverlay {
        private AMap mamap;
        private List<PoiItem> mPois;
        private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();

        public myPoiOverlay(AMap amap, List<PoiItem> pois) {
            mamap = amap;
            mPois = pois;
        }

        /**
         * 添加Marker到地图中。
         *
         * @since V2.1.0
         */
        public void addToMap() {
            address_list = new ArrayList<>();

            for (int i = 0; i < mPois.size(); i++) {
                Marker marker = null;
                PoiItem mCurrentPoi = mPois.get(i);
                Map map = new HashMap();
                map.put("title", mCurrentPoi + "");
                map.put("address", mCurrentPoi.getSnippet() + "");
                address_list.add(map);

                if (i == 0) {
                    location.setText(mPois.get(i).getSnippet());
                }

                //getMarkerData(marker);

            }

            MarkCityAdapter adapter = new MarkCityAdapter(MarkPositionActivity.this, address_list);
            address.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            //底下条目点击事件
            address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Map<String, String> stringStringMap = address_list.get(position);
                    String address = stringStringMap.get("address");

                    Log.i("print", "期待的地址address--" + address);

                    String url = String.format(Constant.URL.GETDATAS, address);

                    Log.i("print", "期待的url---" + url);

                    OkHttpUtil.downJSON(url, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) throws JSONException {
                            if (json != null) {
                                final Beannloaction beannloaction = new Gson().fromJson(json, Beannloaction.class);

                                //防止这个东西为空
                                if (beannloaction == null) {
                                    Toast.makeText(MarkPositionActivity.this, "无法获取到数据", Toast.LENGTH_LONG).show();
                                    return;
                                }


                                final List<Beannloaction.GeocodesBean> geocodes =
                                        beannloaction.getGeocodes();

                                //防止这个东西为空
                                if (geocodes.size() == 0) {
                                    Toast.makeText(MarkPositionActivity.this, "无法获取到数据", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                final String location = geocodes.get(0).getLocation();

                                Double lng = Double.valueOf(location.substring(0, location.indexOf(",")));
                                Double lat = Double.valueOf(location.substring(location.indexOf(",") + 1));

                                Log.i("print", "1----" + lng);
                                Log.i("print", "2----" + lat);

                                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14));

                                doSearchQuery(new LatLonPoint(lng, lat));
                            }
                        }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });

                }
            });

        }

        public int getPoiIndex(Marker marker) {
            for (int i = 0; i < mPoiMarks.size(); i++) {
                if (mPoiMarks.get(i).equals(marker)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 移动镜头到当前的视角。
         *
         * @since V2.1.0
         */
        public void zoomToSpan() {
            if (mPois != null && mPois.size() > 0) {
                if (mamap == null)
                    return;
                LatLngBounds bounds = getLatLngBounds();
                if (flag == 1) {
                    mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));//修改视角的.
                    flag++;
                }

//                mamap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                        new LatLng(lp.getLatitude(),lp.getLongitude()), 18, 30, 30)));
            }
        }

        private LatLngBounds getLatLngBounds() {
            LatLngBounds.Builder b = LatLngBounds.builder();
            for (int i = 0; i < mPois.size(); i++) {
                b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
                        mPois.get(i).getLatLonPoint().getLongitude()));
            }
            return b.build();
        }

        private MarkerOptions getMarkerOptions(int index) {

            return new MarkerOptions()
                    .position(
                            new LatLng(mPois.get(index).getLatLonPoint()
                                    .getLatitude(), mPois.get(index)
                                    .getLatLonPoint().getLongitude()))
                    .title(getTitle(index)).snippet(getSnippet(index))
                    .icon(getBitmapDescriptor(index));

        }

        protected String getTitle(int index) {
            return mPois.get(index).getTitle();
        }

        protected String getSnippet(int index) {
            return mPois.get(index).getSnippet();
        }

        protected BitmapDescriptor getBitmapDescriptor(int arg0) {
            BitmapDescriptor icon;
            icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gps_point));
            return icon;
        }
    }
}

