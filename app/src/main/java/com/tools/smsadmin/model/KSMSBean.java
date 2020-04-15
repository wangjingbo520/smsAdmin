package com.tools.smsadmin.model;

import java.util.List;

public class KSMSBean {


    /**
     * code : 200
     * msg : 查询成功
     * data : [{"username":"15575163734","uploadSucessSize":"15000","uploadFailedSize":"25999","times":"2019-12-30","remaining_size":"6500000.0"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : 15575163734
         * uploadSucessSize : 15000
         * uploadFailedSize : 25999
         * times : 2019-12-30
         * remaining_size : 6500000.0
         */

        private String username;
        private String uploadSucessSize;
        private String uploadFailedSize;
        private String times;
        private String remaining_size;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUploadSucessSize() {
            return uploadSucessSize;
        }

        public void setUploadSucessSize(String uploadSucessSize) {
            this.uploadSucessSize = uploadSucessSize;
        }

        public String getUploadFailedSize() {
            return uploadFailedSize;
        }

        public void setUploadFailedSize(String uploadFailedSize) {
            this.uploadFailedSize = uploadFailedSize;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getRemaining_size() {
            return remaining_size;
        }

        public void setRemaining_size(String remaining_size) {
            this.remaining_size = remaining_size;
        }
    }
}
