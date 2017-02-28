package com.tianyi.chulaibar.bean;

/**
 * @
 * @类名称: ${}
 * @类描述: ${type_name}
 * @创建人： Lyp
 * @创建时间：${date} ${time}
 * @备注：
 */
public class Beab_onetuopian {


    /**
     * code : 1
     * type : ok
     * info :
     * result : /upload/images/2016102343035450WJHD.jpg
     */

    private int code;
    private String type;
    private String info;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Beab_onetuopian{" +
                "code=" + code +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
