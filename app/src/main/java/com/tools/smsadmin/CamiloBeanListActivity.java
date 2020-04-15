package com.tools.smsadmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CamiloBeanListActivity extends AppCompatActivity implements IHandleMessage {

    public MyVolleyHandler<CamiloBeanListActivity> mHandler;

    private int vip_type;

    private String username = "";

    private List<CamiloBean> camiloBeans;

    public static void start(Context context, int vip_type, String username) {
        Intent starter = new Intent(context, CamiloBeanListActivity.class);
        starter.putExtra("vip_type", vip_type);
        starter.putExtra("username", username);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camilo_bean_list);
        mHandler = new MyVolleyHandler<>(this);
        vip_type = getIntent().getIntExtra("vip_type", 0);
        username = getIntent().getStringExtra("username");
        camiloBeans = new ArrayList<>();
        queryCamiloList();
    }

    @Override
    public void onHandleMessage(Message msg) {
        if (msg.what == Constants.CODE_RESULT) {
            Bundle data = msg.getData();
            String response = data.getString("response");
            String interfaceMethod = data.getString("interfaceMethod");
            if (interfaceMethod.equals(InterfaceMethod.USER_CAMILO_DATA)) {
                //列表
            }
            UserBean userBean = new Gson().fromJson(response, UserBean.class);
            ToastUtil.showMessage(userBean.getMsg());
            finish();
        }

    }


    private void queryCamiloList() {
        String adminName = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
        Map<String, String> params = new HashMap<>();
        params.put("admin_name", adminName);
        params.put("type", vip_type + "");
        params.put("status", "1");
        RequestHandler.addRequest(Request.Method.POST, this, mHandler, Constants.CODE_RESULT,
                params, null, false, InterfaceMethod.USER_CAMILO_DATA);
    }




//    public class MyAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder = null;
//            if (convertView == null) {
//                viewHolder = new ViewHolder();
//                convertView = LayoutInflater.from(CamiloBeanListActivity.this).inflate(R.layout.layout_choose, null);
//                viewHolder.tv_zcbh = convertView.findViewById(R.id.tv_zcbh);
//                viewHolder.tv_sybm = convertView.findViewById(R.id.tv_sybm);
//                viewHolder.checkbox = convertView.findViewById(R.id.checkbox);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            viewHolder.tv_zcbh.setText("资产编号:" + getItem(position).gdzcbh);
//
//            viewHolder.tv_sybm.setText("使用/分管部门：" + list_sybm.get(position));
//
//            viewHolder.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
//                list_checkdetail.get(position).setCheck(b);
//                if (b) {
//                    sparseArray.put(position, getItem(position));
//                } else {
//                    sparseArray.remove(position);
//                }
//            });
//
//            viewHolder.checkbox.setChecked(list_checkdetail.get(position).isCheck());
//
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView camilo;
//            TextView status
//        }
//    }
}
