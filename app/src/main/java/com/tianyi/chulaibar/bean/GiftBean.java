package com.tianyi.chulaibar.bean;

/**
 * Created by thinkpad on 2016/12/25.
 */
public class GiftBean {

    private String giftName;
    private String giftNumber;
    private String giftShuoMing;

    public GiftBean(String giftName, String giftNumber, String giftShuoMing) {
        this.giftName = giftName;
        this.giftNumber = giftNumber;
        this.giftShuoMing = giftShuoMing;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(String giftNumber) {
        this.giftNumber = giftNumber;
    }

    public String getGiftShuoMing() {
        return giftShuoMing;
    }

    public void setGiftShuoMing(String giftShuoMing) {
        this.giftShuoMing = giftShuoMing;
    }

    @Override
    public String toString() {
        return "礼物名称："+giftName+"  数量:"+giftNumber+"  说明:"+giftShuoMing;
    }
}
