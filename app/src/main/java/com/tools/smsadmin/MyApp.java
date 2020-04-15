package com.tools.smsadmin;

import android.app.Application;


/**
 * @author wjb
 * describe
 */
public class MyApp extends Application {
    public static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;

    }

    public static MyApp getInstance() {
        return myApp;
    }


}
