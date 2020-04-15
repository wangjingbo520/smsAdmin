package com.tools.smsadmin.views;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.tools.smsadmin.R;

/**
 * @author wjb
 * describe
 */
public class TipDialog extends AlertDialog {
    private TextView tv_dialog_text;
    private ImageView iv_dialog_text_cancel;
    private Button btn_dialog_text_ok;
    //private OnDialogTextClick mOnDialogTextClick;
    private String mText;
    private Activity mActivity;

    public TipDialog(Activity activity) {
        super(activity, R.style.DialogImageTheme);
        mActivity = activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_text);

        tv_dialog_text = (TextView) findViewById(R.id.tv_dialog_text);
        iv_dialog_text_cancel = (ImageView) findViewById(R.id.iv_dialog_text_cancel);
        btn_dialog_text_ok = (Button) findViewById(R.id.btn_dialog_text_ok);

        iv_dialog_text_cancel.setOnClickListener(v -> dismiss());
        initUI();
    }

    private void initUI() {
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

//        iv_dialog_text_cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (mOnDialogTextClick != null) {
//                    mOnDialogTextClick.onClick(0);
//                }
//            }
//        });

    }

//    public void setOnOKClickListener(String okText, OnDialogTextClick onDialogTextClick) {
//
//        if (mActivity != null && mActivity.isFinishing()) {
//            return;
//        }
//
//        if (okText != null && !okText.equals("")) {
//            btn_dialog_text_ok.setText(okText);
//        }
//
//        btn_dialog_text_ok.setVisibility(View.VISIBLE);
//
//        btn_dialog_text_ok.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (mOnDialogTextClick != null) {
//                    mOnDialogTextClick.onClick(1);
//                }
//            }
//        });
//    }

    public void showText(String text) {
        mText = text;
        show();
        if (tv_dialog_text != null) {
            tv_dialog_text.setText(text);
        }
    }


    @Override
    public void show() {
        if (mActivity != null && !mActivity.isFinishing() && !isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (mActivity != null && isShowing()) {
//            if (mOnDialogTextClick != null) {
//              //  mOnDialogTextClick.dismiss();
//            }
            super.dismiss();
        }
    }

    public boolean isDead() {
        return mActivity.isFinishing();
    }
}

