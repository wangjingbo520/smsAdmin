package com.tools.smsadmin.http;

import android.os.Message;

/**
 * @author   wjb
 * describe
 */
public interface IHandleMessage {
    /**
     * volley回调
     *
     * @param msg message
     */
    void onHandleMessage(Message msg);
}