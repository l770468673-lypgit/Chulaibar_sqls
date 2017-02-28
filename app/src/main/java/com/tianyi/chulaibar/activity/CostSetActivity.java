package com.tianyi.chulaibar.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.bean.CostBean;
import com.tianyi.chulaibar.bean.GiftBean;

import java.util.ArrayList;
import java.util.List;

public class CostSetActivity extends BaseActivity implements View.OnClickListener{


    private TextView tv_refundset_activity_cost,tv_additem,tv_queding_activity_cost;

    private LinearLayout ll_activity_cost;

    private List<CostBean> costBeans;


    @Override
    protected int getContentResid() {
        return R.layout.activity_cost_set;
    }

    @Override
    protected void init() {
        //退款设置
        tv_refundset_activity_cost= (TextView) findViewById(R.id.tv_refundset_activity_cost);
        tv_refundset_activity_cost.setOnClickListener(this);
        ll_activity_cost = (LinearLayout) findViewById(R.id.ll_activity_cost);
        tv_additem = (TextView) findViewById(R.id.tv_additem);
        tv_additem.setOnClickListener(this);
        tv_queding_activity_cost = (TextView) findViewById(R.id.tv_queding_activity_cost);
        tv_queding_activity_cost.setOnClickListener(this);

        costBeans = new ArrayList<>();
    }

    @Override
    protected void loadDatas() {


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_refundset_activity_cost:

                Intent refundIntent = new Intent(this,RefundSetActivity.class);
                startActivity(refundIntent);

                break;

            case R.id.tv_additem:

                addColums();

                break;

            case R.id.tv_queding_activity_cost:

                Toast.makeText(CostSetActivity.this, "完成设置", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < ll_activity_cost.getChildCount(); i++) {

                    View view = ll_activity_cost.getChildAt(i);
                    EditText et_name_activity_cost_set = (EditText) view.findViewById(R.id.et_name_activity_cost_set);
                    EditText et_moneny_activity_cost_set = (EditText) view.findViewById(R.id.et_moneny_activity_cost_set);
                    EditText et_shuoming_activity_cost_set = (EditText) view.findViewById(R.id.et_shuoming_activity_cost_set);

                    String giftName = et_name_activity_cost_set.getText().toString().trim();
                    String giftNumber = et_moneny_activity_cost_set.getText().toString().trim();
                    String giftShuoMing = et_shuoming_activity_cost_set.getText().toString().trim();

                    costBeans.add(new CostBean(giftName,giftNumber,giftShuoMing));

                }

                //可删除//////////////////////////////////////////////
                String str = "";
                for(int i=0; i<costBeans.size();i++){

                    str += costBeans.get(i).toString()+"\n";

                }

                Toast.makeText(CostSetActivity.this,str,Toast.LENGTH_SHORT).show();
                /////////////////////end/////////////////////////////////
                costBeans.clear();


                break;

        }


    }

    private void addColums(){
        final View view = LayoutInflater.from(this).inflate(R.layout.item_lv_activity_cost_set, null);

        ImageView iv_delete_activity_cost = (ImageView) view.findViewById(R.id.iv_delete_activity_cost);

        iv_delete_activity_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CostSetActivity.this, "点击删除了！", Toast.LENGTH_SHORT).show();

                ll_activity_cost.removeView(view);
            }
        });

        ll_activity_cost.addView(view);
    }

}
