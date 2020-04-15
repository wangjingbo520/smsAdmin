package com.tools.smsadmin;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import com.tools.smsadmin.views.TipDialog;

/**
 * @author wjb
 * describe
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private TipDialog mDialogText;

    protected void showDialogText(final String text) {
        runOnUiThread(() -> {
            if (mDialogText == null || mDialogText.isDead()) {
                mDialogText = new TipDialog(BaseActivity.this);
            }
            mDialogText.showText(text);
        });
    }

}
