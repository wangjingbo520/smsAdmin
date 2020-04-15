package com.tools.smsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.tools.smsadmin.http.IHandleMessage;
import com.tools.smsadmin.http.InterfaceMethod;
import com.tools.smsadmin.http.MyVolleyHandler;
import com.tools.smsadmin.http.RequestHandler;
import com.tools.smsadmin.model.UserInfos;
import com.tools.smsadmin.utils.Constants;
import com.tools.smsadmin.utils.DateUtils;
import com.tools.smsadmin.utils.SPUtils;
import com.tools.smsadmin.utils.ToastUtil;
import com.tools.smsadmin.utils.UserListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IHandleMessage, UserListAdapter.IResetInterface {

    @BindView(R.id.listview)
    ListView listview;
    private UserListAdapter userListAdapter;
    private List<UserInfos.DataBean> list;

    public MyVolleyHandler<MainActivity> mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mHandler = new MyVolleyHandler<>(this);
        list = new ArrayList<>();
        listview.setOnItemClickListener(onItemClickListener);
        findViewById(R.id.tv_exit).setOnClickListener(v -> showExitDialog());
        findViewById(R.id.tv_refresh).setOnClickListener(v -> getData());
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list == null || list.size() < 1) {
                return;
            }

            int openning = list.get(position).getOpening();
            String camilo = list.get(position).getCamilo();
            ManageActivity.start(MainActivity.this, list.get(position).getUser_id(), list.get(position).getUsername(), openning, camilo);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        String admin_name = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
        Map<String, String> params = new HashMap<>();
        params.put("admin_name", admin_name);
        RequestHandler.addRequest(Request.Method.POST, this, mHandler, Constants.CODE_RESULT,
                params, null, true, InterfaceMethod.QUERY_ALL_USER);
    }

    @Override
    public void onHandleMessage(Message msg) {
        if (msg.what == Constants.CODE_RESULT) {
            Bundle data = msg.getData();
            String interfaceMethod = data.getString("interfaceMethod");
            String response = data.getString("response");
            if (interfaceMethod.equals(InterfaceMethod.QUERY_ALL_USER)) {
                try {
                    list.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jarr = jsonObject.getJSONArray("data");
                    if (jarr != null) {
                        if (jarr.length() <= 0) {
                            ToastUtil.showMessage("还没有数据哦......");
                        } else {
                            for (int i = 0; i < jarr.length(); i++) {
                                UserInfos.DataBean dataBean = new UserInfos.DataBean();
                                JSONObject obj = jarr.getJSONObject(i);
                                dataBean.setUsername(obj.getString("username"));
                                dataBean.setEnd_date(obj.getString("end_date"));
                                dataBean.setStart_date(obj.getString("start_date"));
                                dataBean.setOpening(obj.getInt("opening"));
                                dataBean.setLogin_status(obj.getString("login_status"));
                                dataBean.setRegister_date(obj.getString("register_date"));
                                dataBean.setUser_id(obj.getInt("user_id"));
                                dataBean.setRemaining_day(obj.getInt("remaining_day"));
                                dataBean.setCamilo(obj.getString("camilo"));
                                list.add(dataBean);
                            }
                            userListAdapter = new UserListAdapter(list, this, this);
                            listview.setAdapter(userListAdapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (interfaceMethod.equals(InterfaceMethod.EXIT_LOGIN)) {
                SPUtils.getInstance().clear();
                ToastUtil.showMessage("成功退出管理员");
                finish();
            } else if (interfaceMethod.equals(InterfaceMethod.RESET_OPENNING)) {
                getData();
                ToastUtil.showMessage("重置成功");
            }
        }
    }


    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.gl);
        builder.setTitle("提示！");
        builder.setMessage("您确定退出登录吗？");
        builder.setPositiveButton("确定", (dialog, which) -> {
            String admin_name = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
            String admin_password = SPUtils.getInstance().getString(Constants.ADMIN_PASSWORD, "");
            Map<String, String> params = new HashMap<>();
            params.put("admin_name", admin_name);
            params.put("admin_password", admin_password);
            RequestHandler.addRequest(Request.Method.POST, MainActivity.this, mHandler, Constants.CODE_RESULT,
                    params, null, true, InterfaceMethod.EXIT_LOGIN);
            dialog.dismiss();

        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onReset(int position) {
        showMyDialog(position);
    }


    private void showMyDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.gl);
        builder.setTitle("提示！");
        builder.setMessage("您确定要重新设置该会员吗？");
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
            if (list.get(position) != null) {
                if (list.get(position).getOpening() == 0) {
                    //
                    resetOpenning(list.get(position).getUsername());
                } else if (list.get(position).getOpening() == 1) {
                    //
                    resetOpenning(list.get(position).getUsername());
                }
            } else {
                ToastUtil.showMessage("该用户不存");
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void resetOpenning(String username) {
        Map<String, String> params = new HashMap<>();
        String admin_name = SPUtils.getInstance().getString(Constants.ADMIN_USERNAME, "");
        params.put("username", username);
        params.put("admin_name", admin_name);
        RequestHandler.addRequest(Request.Method.POST, MainActivity.this, mHandler, Constants.CODE_RESULT,
                params, null, true, InterfaceMethod.RESET_OPENNING);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_1:
                startActivity(new Intent(this, KSMSActivity.class));
                break;
            case R.id.action_2:
                startActivity(new Intent(this, SearchUserActivity.class));
                break;
            case R.id.action_3:
                startActivity(new Intent(this, ProductCamiloActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

