package com.tools.smsadmin.model;

import java.util.List;

/**
 * @author wjb（C）
 * describe
 */
public class UserInfos {


    /**
     * code : 200
     * msg : ok
     * data : [{"user_id":2,"username":"15575163734","password":"fbade9e36a3f36d3d676c1b808451dd7","login_status":"已登录","register_date":"2019-12-22","start_date":"未知日期","end_date":"未知日期","opening":0},{"user_id":3,"username":"17304463597","password":"rwerewterterytryrtyrtytryrtyrty","login_status":"已登录","register_date":"2019-12-23","start_date":"未知日期","end_date":"未知日期","opening":0}]
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
         * user_id : 2
         * username : 15575163734
         * password : fbade9e36a3f36d3d676c1b808451dd7
         * login_status : 已登录
         * register_date : 2019-12-22
         * start_date : 未知日期
         * end_date : 未知日期
         * opening : 0
         */

        private int user_id;
        private String username;
        private String password;
        private String login_status;
        private String register_date;
        private String start_date;
        private String end_date;
        private int opening;
        private int remaining_day;
        private String camilo;

        public String getCamilo() {
            return camilo;
        }

        public void setCamilo(String camilo) {
            this.camilo = camilo;
        }

        public int getRemaining_day() {
            return remaining_day;
        }

        public void setRemaining_day(int remaining_day) {
            this.remaining_day = remaining_day;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLogin_status() {
            return login_status;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }

        public String getRegister_date() {
            return register_date;
        }

        public void setRegister_date(String register_date) {
            this.register_date = register_date;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getOpening() {
            return opening;
        }

        public void setOpening(int opening) {
            this.opening = opening;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id=" + user_id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", login_status='" + login_status + '\'' +
                    ", register_date='" + register_date + '\'' +
                    ", start_date='" + start_date + '\'' +
                    ", end_date='" + end_date + '\'' +
                    ", opening=" + opening +
                    '}';
        }
    }
}
