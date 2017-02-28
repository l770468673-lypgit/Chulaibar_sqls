package com.tianyi.chulaibar.bean;

import java.util.List;

/**
 * @
 * @类名称: ${}
 * @类描述: ${type_name}
 * @创建人： Lyp
 * @创建时间：${date} ${time}
 * @备注：
 */
public class Bean_MoBanPic {


    /**
     * id : 1
     * attachName : 创业类
     * attachPath : upload/YuLiu/chuangye.jpg
     */

    private List<AttachmentListBean> attachmentList;

    public List<AttachmentListBean> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<AttachmentListBean> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public static class AttachmentListBean {
        private int id;
        private String attachName;
        private String attachPath;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }

        public String getAttachPath() {
            return attachPath;
        }

        public void setAttachPath(String attachPath) {
            this.attachPath = attachPath;
        }

        @Override
        public String toString() {
            return "AttachmentListBean{" +
                    "id=" + id +
                    ", attachName='" + attachName + '\'' +
                    ", attachPath='" + attachPath + '\'' +
                    '}';
        }
    }
}
