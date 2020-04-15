package com.tools.smsadmin;

import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.tools.smsadmin.http.IHandleMessage;
import com.tools.smsadmin.http.InterfaceMethod;
import com.tools.smsadmin.http.MyVolleyHandler;
import com.tools.smsadmin.http.RequestHandler;
import com.tools.smsadmin.model.KSMSBean;
import com.tools.smsadmin.utils.Constants;
import com.tools.smsadmin.utils.KSMSListAdapter;
import com.tools.smsadmin.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KSMSActivity extends AppCompatActivity implements IHandleMessage {

    public MyVolleyHandler<KSMSActivity> mHandler;

    private ListView listView;

    private List<KSMSBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ksms);
        list = new ArrayList<>();
        listView = findViewById(R.id.listview);
        mHandler = new MyVolleyHandler<>(this);
        findViewById(R.id.tv_refresh).setOnClickListener(v -> getData());
        getData();
    }

    private void getData() {
        String admin_name = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
        Map<String, String> params = new HashMap<>();
        params.put("admin_name", admin_name);
        RequestHandler.addRequest(Request.Method.POST, this, mHandler, Constants.CODE_RESULT,
                params, null, true, InterfaceMethod.KSMSINFO);
    }


    @Override
    public void onHandleMessage(Message msg) {
        if (msg.what == Constants.CODE_RESULT) {
            Bundle data = msg.getData();
            String response = data.getString("response");
            try {
                list.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jarr = jsonObject.getJSONArray("data");
                for (int i = 0; i < jarr.length(); i++) {
                    KSMSBean.DataBean dataBean = new KSMSBean.DataBean();
                    JSONObject obj = jarr.getJSONObject(i);
                    dataBean.setUsername(obj.getString("username"));
                    dataBean.setUploadSucessSize(obj.getString("uploadSucessSize"));
                    dataBean.setUploadFailedSize(obj.getString("uploadFailedSize"));
                    dataBean.setTimes(obj.getString("times"));
                    dataBean.setRemaining_size(obj.getString("remaining_size"));
                    list.add(dataBean);
                }
                listView.setAdapter(new KSMSListAdapter(this, list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
