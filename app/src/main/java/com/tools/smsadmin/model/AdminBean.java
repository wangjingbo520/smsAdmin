package com.tools.smsadmin.model;

/**
 * @author wjb（C）
 * describe
 */
public class AdminBean  {


    /**
     * code : 200
     * msg : 管理员登录成功
     * data : {"admin_name":"15575163734","admin_password":"123456","id":1,"admin_login_status":"已登录"}
     */

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
        /**
         * admin_name : 15575163734
         * admin_password : 123456
         * id : 1
         * admin_login_status : 已登录
         */

        private String admin_name;
        private String admin_password;
        private int id;
        private String admin_login_status;

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getAdmin_password() {
            return admin_password;
        }

        public void setAdmin_password(String admin_password) {
            this.admin_password = admin_password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdmin_login_status() {
            return admin_login_status;
        }

        public void setAdmin_login_status(String admin_login_status) {
            this.admin_login_status = admin_login_status;
        }
    }
}
