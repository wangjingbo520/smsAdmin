package com.tools.smsadmin.model;

/**
 * @author wjb
 * describe
 */
public class BaseResponse {
    public String msg;
    public int code;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
