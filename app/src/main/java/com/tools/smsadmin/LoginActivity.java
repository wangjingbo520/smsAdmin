package com.tools.smsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.tools.smsadmin.http.IHandleMessage;
import com.tools.smsadmin.http.InterfaceMethod;
import com.tools.smsadmin.http.MyVolleyHandler;
import com.tools.smsadmin.http.RequestHandler;
import com.tools.smsadmin.model.AdminBean;
import com.tools.smsadmin.utils.Constants;
import com.tools.smsadmin.utils.SPUtils;
import com.tools.smsadmin.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements IHandleMessage {
    private static final String TAG = "========>";
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPasword)
    EditText etPasword;
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    public MyVolleyHandler<LoginActivity> mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler = new MyVolleyHandler<>(this);
        tv_sure.setOnClickListener(v -> {
            login();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String admin_name = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
        Log.e(TAG, "admin_name: " + admin_name);
        String admin_passwod = SPUtils.getInstance().getString(Constants.ADMIN_PASSWORD, "");
        Log.e(TAG, "admin_passwod: " + admin_passwod);
        etUsername.setText(admin_name);
        etPasword.setText(admin_passwod);
    }

    private void login() {
        String userName = etUsername.getText().toString().trim();
        String password = etPasword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showMessage("用户名不能为空！");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.showMessage("密码不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("admin_name", userName);
        //  params.put("password", MD5.md5(password));
        params.put("admin_password", password);
        RequestHandler.addRequest(Request.Method.POST, this, mHandler, Constants.CODE_RESULT,
                params, null, true, InterfaceMethod.LOGIN);

    }

    @Override
    public void onHandleMessage(Message msg) {
        if (msg.what == Constants.CODE_RESULT) {
            Bundle data = msg.getData();
            String response = data.getString("response");
            AdminBean adminBean = new Gson().fromJson(response, AdminBean.class);
            ToastUtil.showMessage(adminBean.getMsg());
            SPUtils.getInstance().put(Constants.ADMIN_USERNAME, adminBean.getData().getAdmin_name());
            SPUtils.getInstance().put(Constants.ADMIN_PASSWORD, adminBean.getData().getAdmin_password());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
