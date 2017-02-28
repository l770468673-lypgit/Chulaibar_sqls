package com.tianyi.chulaibar.bean;

/**
 * Created by thinkpad on 2016/12/25.
 */
public class CostBean {
    private String costName;
    private String costPrice;
    private String costShuoMing;

    public CostBean(String costName, String costPrice, String costShuoMing) {
        this.costName = costName;
        this.costPrice = costPrice;
        this.costShuoMing = costShuoMing;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getCostShuoMing() {
        return costShuoMing;
    }

    public void setCostShuoMing(String costShuoMing) {
        this.costShuoMing = costShuoMing;
    }
}
