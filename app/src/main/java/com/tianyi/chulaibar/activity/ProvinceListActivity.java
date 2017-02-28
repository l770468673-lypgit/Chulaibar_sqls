package com.tianyi.chulaibar.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.bean.ProvinceBean;
import com.tianyi.chulaibar.bean.ProvinceCityListBean;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.GsonUtil;
import com.tianyi.chulaibar.util.L;
import com.tianyi.chulaibar.util.OkHttpUtil;
import com.tianyi.chulaibar.util.Utils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class   ProvinceListActivity extends BaseActivity {
    private ListView procince_list;
    private List<Map<String, Object>> provincelist;
    private TextView title;
    int clickCount;
    private StringBuffer stringBuffer;
    private int REQUEST_CODE = 1;
    int provinceId = 0;
    String url = null;
    List<Map<String, Object>> cityList;
    private ProgressDialog mDialog;
    @Override
    protected int getContentResid() {
        return R.layout.activity_province_list;
    }

    @Override
    protected void init() {
        procince_list = (ListView) findViewById(R.id.procince_list);
        title = (TextView) findViewById(R.id.title);
        stringBuffer = new StringBuffer();
        cityList = new ArrayList();
    }

    @Override
    protected void loadDatas() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("数据加载中......");
        mDialog.show();
        useOkHttpDownLoad();

    }

    private void useOkHttpDownLoad() {
        String url = String.format(Constant.URL.PROVINCE_LIST_URL);
        OkHttpUtil.downJSON(url, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                if (json != null) {
                    mDialog.dismiss();
                    Gson gson = GsonUtil.getInstance();
                    ProvinceBean provinceBean = gson.fromJson(json, ProvinceBean.class);
                    provincelist = new ArrayList();
                    for (int i = 0; i < provinceBean.getProvinceList().size(); i++) {
                        Map map = new HashMap();
                        map.put("provinceId", provinceBean.getProvinceList().get(i).getProvinceId());
                        map.put("provinceName", provinceBean.getProvinceList().get(i).getProvinceName());
                        map.put("enId", provinceBean.getProvinceList().get(i).getEnId());
                        map.put("hot", provinceBean.getProvinceList().get(i).isHot());
                        provincelist.add(map);
                    }
                    procince_list.setAdapter(new SimpleAdapter(ProvinceListActivity.this, provincelist, R.layout.province_item, new String[]{"provinceName"}, new int[]{R.id.text}));
                    initListViewLinister();
                }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    private void initListViewLinister() {
        procince_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                clickCount++;
                final SimpleAdapter[] adapter = new SimpleAdapter[1];
                if (clickCount == 1) {
                    provinceId = (int) provincelist.get(position).get("provinceId");
                    stringBuffer.append(provincelist.get(position).get("provinceName"));
                    url = String.format(Constant.URL.CIITY_LIST_URL, provinceId);
                    OkHttpUtil.downJSON(url, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) throws JSONException {

                            if (json != null) {
                                Gson gson = GsonUtil.getInstance();
                                ProvinceCityListBean cityListBean = gson.fromJson(json, ProvinceCityListBean.class);

                                title.setText("请选择所在地区");
                                for (int i = 0; i < cityListBean.getCityList().size(); i++) {
                                    Map map = new HashMap();
                                    map.put("cityId", cityListBean.getCityList().get(i).getCityId());
                                    map.put("cityName", cityListBean.getCityList().get(i).getCityName());
                                    map.put("zipCode", cityListBean.getCityList().get(i).getZipCode());
                                    map.put("provinceId", cityListBean.getCityList().get(i).getProvinceId());
                                    map.put("hot", cityListBean.getCityList().get(i).isHot());
                                    cityList.add(map);
                                }
                                adapter[0] = new SimpleAdapter(ProvinceListActivity.this, cityList, R.layout.province_item, new String[]{"cityName"}, new int[]{R.id.text});
                                procince_list.setAdapter(adapter[0]);
                                adapter[0].notifyDataSetChanged();
                            }
                         }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });
                } else if (clickCount == 2) {
                    if (cityList != null && cityList.toString() != "") {
                        String address = stringBuffer.append(cityList.get(position).get("cityName")).toString();
                        Intent intent = new Intent(ProvinceListActivity.this, MainActivity.class);
                        String cityId = stringBuffer.append(cityList.get(position).get("cityId")).toString();

                        String subcity = Utils.getNumber(cityId);

                        String provinceId = stringBuffer.append(cityList.get(position).get("provinceId")).toString();
                        String subproviceid = Utils.getNumber(provinceId);

                        Bundle bundle = new Bundle();
                        bundle.putString("address", address);
                        bundle.putString("cityId", subcity);
                        bundle.putString("provinceId", subproviceid);
                        intent.putExtras(bundle);
                        setResult(REQUEST_CODE, intent);
                        finish();
                        L.d("ssssssssssssssssss", "cityId " + subcity + "provinceId" + subproviceid);
                    }

                }

            }
        });
    }




}
