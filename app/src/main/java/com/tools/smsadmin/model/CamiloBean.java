package com.tools.smsadmin.model;

/**
 * @author wjb（C）
 * describe
 */

public class CamiloBean {


    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(String camilo, int type, int status, String fileName) {
            this.camilo = camilo;
            this.type = type;
            this.status = status;
            this.fileName = fileName;
        }

        private String camilo;
        private int type;
        //是否正在使用是否正在使:
        //1：未使用
        //2：正在使用
        private int status;

        private String fileName;

        public String getCamilo() {
            return camilo;
        }

        public void setCamilo(String camilo) {
            this.camilo = camilo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}
