package com.tools.smsadmin.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.tools.smsadmin.R;


/**
 * @author Bobo
 * @date 2019/9/21
 * describe
 */
public class LoadingDialog extends BaseDialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected View getDefaultView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        return v;
    }
}
