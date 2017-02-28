package com.tianyi.chulaibar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class ActivityTypeListBean {


    /**
     * result : [{"typeId":1,"typeName":"亲子","description":null,"status":false,"sort":0},{"typeId":2,"typeName":"娱乐","description":null,"status":false,"sort":0},{"typeId":3,"typeName":"社交","description":null,"status":false,"sort":0},{"typeId":4,"typeName":"运动","description":null,"status":false,"sort":0},{"typeId":5,"typeName":"文化","description":null,"status":false,"sort":0},{"typeId":6,"typeName":"公益","description":null,"status":false,"sort":0},{"typeId":7,"typeName":"其他","description":null,"status":false,"sort":0}]
     * code : 0
     * type : SUCCESS
     * info :
     */

    private int code;
    private String type;
    private String info;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * typeId : 1
         * typeName : 亲子
         * description : null
         * status : false
         * sort : 0
         */

        private int typeId;
        private String typeName;
        private Object description;
        private boolean status;
        private int sort;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
