package com.tools.smsadmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.tools.smsadmin.http.IHandleMessage;
import com.tools.smsadmin.http.InterfaceMethod;
import com.tools.smsadmin.http.MyVolleyHandler;
import com.tools.smsadmin.http.RequestHandler;
import com.tools.smsadmin.model.CamiloBean;
import com.tools.smsadmin.model.UserBean;
import com.tools.smsadmin.utils.Constants;
import com.tools.smsadmin.utils.SPUtils;
import com.tools.smsadmin.utils.ToastUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageActivity extends AppCompatActivity implements IHandleMessage {
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.vip_number)
    TextView vip_number;
    @BindView(R.id.tvOpenning)
    TextView tvOpenning;
    @BindView(R.id.ll)
    LinearLayout ll;

    private int user_id;

    private int mYear, mMonth, mDay;

    public MyVolleyHandler<ManageActivity> mHandler;
    private DatePickerDialog datePickerDialog;

    private int tag_day = 0;

    private int openning = 0;

    private String[] types = new String[]{"一个月", "两个月", "三个月"};

    private int[] vipArr = new int[]{1, 2, 3};

    private AlertDialog.Builder alertBuilder;
    private AlertDialog alertDialog;

    private int vip_type = 1;

    private String username = "";

    public static void start(Context context, int user_id, String username, int openning, String camilo) {
        Intent starter = new Intent(context, ManageActivity.class);
        starter.putExtra("user_id", user_id);
        starter.putExtra("username", username);
        starter.putExtra("camilo", camilo);
        starter.putExtra("openning", openning);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("设置使用时间");
        }
        ButterKnife.bind(this);
        mHandler = new MyVolleyHandler<>(this);
        user_id = getIntent().getIntExtra("user_id", 0);
        openning = getIntent().getIntExtra("openning", 0);
        username = getIntent().getStringExtra("username");

        tvOpenning.setText(openning == 0 ? "会员状态： 未开通" : "会员状态：  已开通");
        tvUsername.setText("用户名： " + getIntent().getStringExtra("username"));

        String camilo = getIntent().getStringExtra("camilo");

        if (openning == 0) {
            ll.setVisibility(View.VISIBLE);
            tvTip.setVisibility(View.VISIBLE);
            vip_number.setText("");
        } else {
            ll.setVisibility(View.GONE);
            tvTip.setVisibility(View.INVISIBLE);
            vip_number.setText(camilo);
        }

        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择VIP类型");

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);

    }


    @OnClick({R.id.tv_vip, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vip:
                selectVip();
                break;
            case R.id.tv_sure:
                doPost(vip_number.getText().toString().trim());
                break;
            default:
                break;
        }
    }


    private void selectVip() {
        vip_type = vipArr[0];
        alertBuilder.setSingleChoiceItems(types, 0, (dialogInterface, i) -> vip_type = vipArr[i]);
        alertBuilder.setPositiveButton("确定", (dialogInterface, i) -> {
            alertDialog.dismiss();
            String adminName = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
            Map<String, String> params = new HashMap<>();
            params.put("admin_name", adminName);
            params.put("type", vip_type + "");
            //0：未使用
            //1：正在使用
            params.put("status", "0");
            RequestHandler.addRequest(Request.Method.POST, ManageActivity.this, mHandler, Constants.CODE_RESULT,
                    params, null, false, InterfaceMethod.USER_CAMILO_ONE);
        });

        alertBuilder.setNegativeButton("取消", (dialogInterface, i) -> alertDialog.dismiss());
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }


    private void doPost(String camilo) {
        if (TextUtils.isEmpty(camilo)) {
            ToastUtil.showMessage("卡密不能为空");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("camilo", camilo);
        RequestHandler.addRequest(Request.Method.POST, this, mHandler, Constants.CODE_RESULT,
                params, null, false, InterfaceMethod.USER_CHARGE);
    }


    @Override
    public void onHandleMessage(Message msg) {
        if (msg.what == Constants.CODE_RESULT) {
            Bundle data = msg.getData();
            String response = data.getString("response");
            String interfaceMethod = data.getString("interfaceMethod");
            if (interfaceMethod.equals(InterfaceMethod.USER_CAMILO_ONE)) {
                //查询出来的一条数据
                CamiloBean camiloBean = new Gson().fromJson(response, CamiloBean.class);
                String camilo = camiloBean.getData().getCamilo();
                vip_number.setText(camilo);
                ToastUtil.showMessage("获取卡密成功");
            } else if (interfaceMethod.equals(InterfaceMethod.USER_CHARGE)) {
                ToastUtil.showMessage("给该用户开通成功");
            }
        }
    }


    private DatePickerDialog.OnDateSetListener mdateListener = (view, year, monthOfYear, dayOfMonth) -> {
        if (tag_day == 0) {
            //开始日期
            //   tvStartTime.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
        } else if (tag_day == 1) {
            //   tvEndTime.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
        } else {
            ToastUtil.showMessage("数据异常");
        }
    };


}
