package com.tools.smsadmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.tools.smsadmin.http.InterfaceMethod;
import com.tools.smsadmin.model.CamiloBean;
import com.tools.smsadmin.utils.CamiloUtils;
import com.tools.smsadmin.utils.FileUtils;
import com.tools.smsadmin.utils.ToastUtil;
import com.tools.smsadmin.views.TipDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

public class ProductCamiloActivity extends BaseActivity {
    private TextView tvTime;
    private TextView tvPath;

    private AlertDialog.Builder alertBuilder;
    private AlertDialog alertDialog;

    private String[] str_arr = new String[]{"一个月", "两个月", "三个月"};
    private final int[] arr_flag = new int[]{1, 2, 3};
    private int type = 1;

    private String absolutePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_camilo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("卡密管理");
        }

        tvTime = findViewById(R.id.tvTime);
        tvPath = findViewById(R.id.tvPath);
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("生成卡密个数");

        new NetTask().execute();

        findViewById(R.id.tv_create).setOnClickListener(v -> {
            showMyDialog();

        });

        findViewById(R.id.tv_upload).setOnClickListener(v -> {
            new ReadTask().execute();
        });
    }

    private void showMyDialog() {
        type = arr_flag[0];
        alertBuilder.setSingleChoiceItems(str_arr, 0, (dialogInterface, i) -> type = arr_flag[i]);
        alertBuilder.setPositiveButton("确定", (dialogInterface, i) -> {
            alertDialog.dismiss();
            createCamilFile();
        });

        alertBuilder.setNegativeButton("取消", (dialogInterface, i) -> alertDialog.dismiss());
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void createCamilFile() {
        if (FileUtils.isSDCardState()) {
            String time = tvTime.getText().toString().trim();
            if (TextUtils.isEmpty(time)) {
                ToastUtil.showMessage("获取网络时间失败，无法生成卡密！！！");
                return;
            }
            new RandomTask().execute();

        } else {
            ToastUtil.showMessage("SDCard存在并且可以读写");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class NetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return CamiloUtils.getNetTime();
        }

        @Override
        protected void onPostExecute(String date) {
            super.onPostExecute(date);
            tvTime.setText(date);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RandomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String message;
            try {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
                absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "卡密文件" + File.separator + date + File.separator +
                        fileName + ".txt";
                FileUtils.writeFile(CamiloUtils.getRandomStr(5),
                        absolutePath, false);
                message = "成功生成100个卡密";
            } catch (IOException e) {
                e.printStackTrace();
                message = e.getMessage();
            }
            return message + ":" + absolutePath;
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            tvPath.setText(message);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class ReadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String json = "";
            if (!TextUtils.isEmpty(absolutePath)) {
                List<CamiloBean.DataBean> beans = FileUtils.readtFile(absolutePath, type);
                json = JSON.toJSONString(beans);
            }
            return json;
        }

        @Override
        protected void onPostExecute(String date) {
            super.onPostExecute(date);
            if (!TextUtils.isEmpty(date)) {
                postJson(date);
            } else {
                showDialogText("不存在文件，请先创建");
            }
        }
    }

    private void postJson(String json) {
        OkHttpUtils
                .postString()
                .url(InterfaceMethod.base_url + "admin/uploadJsonData")
                .content(json)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showMessage(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        showDialogText(response);
                    }
                });
    }


}
